import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getPublicForm, getPublicFormMeta } from '../api/formService';

import { validateToken } from '../api/tokenService';
import { toast } from 'react-toastify';
import { FaLock, FaPaperPlane } from 'react-icons/fa';
import './PublicFormAccess.css';

const PublicFormAccess = () => {
    const { publicLink } = useParams();
    const [form, setForm] = useState(null);
    const [loading, setLoading] = useState(true);
    const [tokenValue, setTokenValue] = useState('');
    const [validating, setValidating] = useState(false);

    useEffect(() => {
        loadForm();
    }, [publicLink]);

    const loadForm = async () => {
        try {
            // First load only metadata (public)
            const data = await getPublicFormMeta(publicLink);
            setForm(data);
        } catch (error) {
            toast.error('Form not found or link invalid');
            setForm(null);
        } finally {
            setLoading(false);
        }
    };

    const handleTokenSubmit = async (e) => {
        e.preventDefault();
        if (!tokenValue.trim()) return;

        try {
            setValidating(true);
            await validateToken(publicLink, tokenValue);
            toast.success('Access granted!');

            // After successful validation, fetch the FULL form content
            const fullData = await getPublicForm(publicLink);
            setForm(fullData);
        } catch (error) {
            toast.error(error.response?.data?.message || 'Invalid or expired token');
        } finally {
            setValidating(false);
        }
    };

    if (loading) return <div className="loader-container">Loading...</div>;
    if (!form) return <div className="loader-container"><h2>Form not found 404</h2></div>;

    // -- STATE 1: ACCESS DENIED (Needs Token) --
    if (!form.content) {
        return (
            <div className="auth-wrapper">
                <div className="glass-panel auth-card">
                    <div className="auth-icon-circle">
                        <FaLock size={32} color="var(--primary)" />
                    </div>

                    <h1 className="auth-title">Protected Form</h1>
                    <p className="auth-subtitle">
                        This form ({form.title}) requires a valid access token.
                    </p>

                    <form onSubmit={handleTokenSubmit}>
                        <input
                            type="text"
                            placeholder="Enter your access token (e.g. OTT-xxx...)"
                            className="input-field auth-input"
                            value={tokenValue}
                            onChange={(e) => setTokenValue(e.target.value)}
                        />
                        <button
                            type="submit"
                            className="btn-primary auth-button"
                            disabled={validating}
                        >
                            {validating ? 'Verifying...' : 'Unlock Access'}
                        </button>
                    </form>
                </div>
            </div>
        );
    }

    // -- STATE 2: ACCESS GRANTED (Show Content) --
    let formContent = {};
    try {
        formContent = typeof form.content === 'string' ? JSON.parse(form.content) : form.content;
    } catch (e) {
        formContent = { title: form.title, description: form.content, questions: [] };
    }

    return (
        <div className="container respondent-container">
            <div className="glass-panel respondent-header">
                <h1 className="respondent-title">{formContent.title || form.title}</h1>
                <p className="respondent-description">{formContent.description}</p>
            </div>

            {formContent.questions && formContent.questions.map((q, index) => (
                <div key={q.id || index} className="glass-panel question-card">
                    <h3 className="question-text">
                        <span className="q-number">{index + 1}.</span>
                        {q.text} {q.required && <span className="q-required">*</span>}
                    </h3>

                    {q.type === 'text' && (
                        <input type="text" className="input-field" placeholder="Your answer..." />
                    )}

                    {q.type === 'single_choice' && (
                        <div className="options-group">
                            {q.options?.map(opt => (
                                <label key={opt} className="option-label">
                                    <input type="radio" name={`q-${q.id}`} className="radio-input" />
                                    {opt}
                                </label>
                            ))}
                        </div>
                    )}

                    {q.type === 'boolean' && (
                        <div className="boolean-group">
                            <label className="option-label">
                                <input type="radio" name={`q-${q.id}`} className="radio-input" /> Yes
                            </label>
                            <label className="option-label">
                                <input type="radio" name={`q-${q.id}`} className="radio-input" /> No
                            </label>
                        </div>
                    )}
                </div>
            ))}

            <div className="submit-section">
                <button className="btn-primary">
                    Submit Form <FaPaperPlane className="submit-btn-icon" />
                </button>
            </div>
        </div>
    );
};

export default PublicFormAccess;
