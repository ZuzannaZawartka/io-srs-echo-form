import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { getFormById, generatePublicLink } from '../api/formService';
import { getTokens, generateToken } from '../api/tokenService';
import { toast } from 'react-toastify';
import { FaArrowLeft, FaLink, FaKey, FaClipboard, FaCheck } from 'react-icons/fa';
import './FormDetails.css';

const FormDetails = () => {
    const { id } = useParams();
    const [form, setForm] = useState(null);
    const [tokens, setTokens] = useState([]);
    const [loading, setLoading] = useState(true);
    const [expiryTime, setExpiryTime] = useState(60);

    const [copiedLink, setCopiedLink] = useState(false);

    useEffect(() => {
        loadData();
    }, [id]);

    const loadData = async () => {
        try {
            setLoading(true);
            const formData = await getFormById(id);
            setForm(formData);

            const tokenData = await getTokens(id);
            setTokens(tokenData);
        } catch (error) {
            toast.error('Failed to load form details');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const handleGenerateLink = async () => {
        try {
            const updatedForm = await generatePublicLink(id);
            setForm(updatedForm);
            toast.success('Public link generated!');
        } catch (error) {
            toast.error('Failed to generate link');
        }
    };

    const handleGenerateToken = async (e) => {
        e.preventDefault();
        try {
            await generateToken(id, expiryTime);
            toast.success(`Token generated for ${expiryTime} mins`);
            const tokenData = await getTokens(id);
            setTokens(tokenData);
        } catch (error) {
            toast.error('Failed to generate token');
        }
    };

    const copyToClipboard = (text) => {
        navigator.clipboard.writeText(text);
        setCopiedLink(true);
        setTimeout(() => setCopiedLink(false), 2000);
        toast.info('Copied to clipboard');
    };

    if (loading) return <div className="container">Loading...</div>;
    if (!form) return <div className="container">Form not found</div>;

    const publicUrl = form.publicLink
        ? `${window.location.origin}/form/${form.publicLink}`
        : null;

    return (
        <div className="container">
            <Link to="/" className="details-back-link">
                <FaArrowLeft /> Back to Dashboard
            </Link>

            <div className="glass-panel">
                <h1 className="details-header-title">{form.title}</h1>
                <div className="details-header-meta">
                    <p>Created: {new Date(form.createdAt + (form.createdAt.endsWith('Z') ? '' : 'Z')).toLocaleString()}</p>
                    <p>ID: {form.id}</p>
                </div>
            </div>

            <div className="details-actions-grid">

                {/* PUBLIC LINK SECTION */}
                <div className="glass-panel">
                    <h2 className="action-card-header">
                        <FaLink color="var(--primary)" /> Public Access
                    </h2>

                    {form.publicLink ? (
                        <div>
                            <p className="public-link-intro">Share this link with respondents:</p>
                            <div className="public-link-row">
                                <input
                                    readOnly
                                    value={publicUrl}
                                    className="input-field input-read-only"
                                />
                                <button
                                    onClick={() => copyToClipboard(publicUrl)}
                                    className="btn-primary"
                                    title="Copy Link"
                                >
                                    {copiedLink ? <FaCheck /> : <FaClipboard />}
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div>
                            <p className="token-intro">Generate a unique public link to share this form.</p>
                            <button onClick={handleGenerateLink} className="btn-primary">
                                Generate Public Link
                            </button>
                        </div>
                    )}
                </div>

                {/* TOKEN GENERATOR SECTION */}
                <div className="glass-panel">
                    <h2 className="action-card-header">
                        <FaKey color="var(--secondary)" /> Generate Token
                    </h2>
                    <form onSubmit={handleGenerateToken}>
                        <label className="token-gen-label">
                            Validity Duration (minutes)
                        </label>
                        <div className="token-gen-row">
                            <select
                                className="input-field"
                                value={expiryTime}
                                onChange={(e) => setExpiryTime(parseInt(e.target.value))}
                            >
                                <option value={30}>30 Minutes</option>
                                <option value={60}>1 Hour</option>
                                <option value={120}>2 Hours</option>
                                <option value={1440}>24 Hours</option>
                            </select>
                            <button type="submit" className="btn-primary">Generate</button>
                        </div>
                    </form>
                </div>
            </div>

            {/* TOKENS LIST */}
            <div>
                <h2 className="tokens-list-header">Access Tokens ({tokens.length})</h2>
                <div className="glass-panel tokens-table-container">
                    <table className="tokens-table">
                        <thead>
                            <tr>
                                <th>Token Value</th>
                                <th>Status</th>
                                <th>Expires At</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {tokens.length === 0 ? (
                                <tr>
                                    <td colSpan="4" className="token-empty">
                                        No tokens generated yet.
                                    </td>
                                </tr>
                            ) : tokens.map(token => {
                                const expiryDate = new Date(token.expiresAt + (token.expiresAt.endsWith('Z') ? '' : 'Z'));
                                const isExpired = new Date() > expiryDate;

                                let badgeClass = 'status-active';
                                let statusText = 'Active';

                                if (isExpired) {
                                    badgeClass = 'status-expired';
                                    statusText = 'Expired';
                                }

                                return (
                                    <tr key={token.tokenValue}>
                                        <td className="token-font">{token.tokenValue}</td>
                                        <td>
                                            <span className={`status-badge ${badgeClass}`}>
                                                {statusText}
                                            </span>
                                        </td>
                                        <td>{new Date(token.expiresAt + (token.expiresAt.endsWith('Z') ? '' : 'Z')).toLocaleString()}</td>
                                        <td>
                                            <button
                                                onClick={() => copyToClipboard(token.tokenValue)}
                                                className="token-btn-copy"
                                                title="Copy Token"
                                            >
                                                <FaClipboard />
                                            </button>
                                        </td>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default FormDetails;
