import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './styles/global.css';
import './styles/components.css';

import Dashboard from './pages/Dashboard';
import FormDetails from './pages/FormDetails';
import PublicFormAccess from './pages/PublicFormAccess';

function App() {
    return (
        <Router>
            <div className="app-container">
                <Routes>
                    {/* Creator Routes */}
                    <Route path="/" element={<Dashboard />} />
                    <Route path="/forms/:id" element={<FormDetails />} />

                    {/* Respondent Routes */}
                    <Route path="/form/:publicLink" element={<PublicFormAccess />} />

                    {/* Catch all */}
                    <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>

                <ToastContainer
                    position="bottom-right"
                    theme="dark"
                    autoClose={3000}
                />
            </div>
        </Router>
    );
}

export default App;
