import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getAllForms, createForm } from '../api/formService';
import { toast } from 'react-toastify';
import { FaPlus, FaFileAlt } from 'react-icons/fa';
import './Dashboard.css';

const Dashboard = () => {
    const [forms, setForms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [creating, setCreating] = useState(false);
    const [newTitle, setNewTitle] = useState('');

    useEffect(() => {
        loadForms();
    }, []);

    const loadForms = async (retryCount = 0) => {
        try {
            const data = await getAllForms();
            setForms(data);
            setLoading(false);
        } catch (error) {
            console.error('Difficulies connecting to backend...', error);
            if (retryCount < 5) {
                // Retry after 2 seconds (Backend might be warming up)
                setTimeout(() => loadForms(retryCount + 1), 2000);
            } else {
                toast.error('Could not connect to server. Please ensure backend is running.');
                setLoading(false);
            }
        }
    };

    const handleCreate = async (e) => {
        e.preventDefault();
        if (!newTitle.trim()) return;

        try {
            setCreating(true);
            await createForm(newTitle, null);
            toast.success('Form created successfully');
            setNewTitle('');
            loadForms();
        } catch (error) {
            toast.error('Failed to create form');
            console.error(error);
        } finally {
            setCreating(false);
        }
    };

    return (
        <div className="container">
            <header className="dashboard-header">
                <h1 className="dashboard-title">
                    EchoForm Dashboard
                </h1>
            </header>

            <div className="glass-panel create-form-section">
                <h2 className="section-title">
                    <FaPlus size={20} color="var(--secondary)" /> Create New Form
                </h2>
                <form onSubmit={handleCreate} className="create-form-row">
                    <input
                        type="text"
                        placeholder="Enter form title..."
                        className="input-field create-form-input"
                        value={newTitle}
                        onChange={(e) => setNewTitle(e.target.value)}
                    />
                    <button type="submit" className="btn-primary" disabled={creating}>
                        {creating ? 'Creating...' : 'Create Form'}
                    </button>
                </form>
            </div>

            <h2 className="forms-list-header">Your Forms</h2>

            {loading ? (
                <p>Loading forms...</p>
            ) : forms.length === 0 ? (
                <p>No forms yet. Create one above!</p>
            ) : (
                <div className="forms-grid">
                    {forms.map(form => (
                        <Link key={form.id} to={`/forms/${form.id}`} className="glass-panel form-card">
                            <div className="form-card-header">
                                <FaFileAlt size={24} color="var(--primary)" />
                                <span className="form-card-id">
                                    ID: {form.id}
                                </span>
                            </div>
                            <h3 className="form-card-title">{form.title}</h3>
                            <p className="form-card-date">
                                Created: {new Date(form.createdAt).toLocaleDateString()}
                            </p>
                        </Link>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Dashboard;
