import api from '../../../../../Downloads/io-srs-echo-form/frontend/src/api/axiosConfig.js';

// Internal API (Creator)
export const generateToken = async (formId, expiresInMinutes) => {
    const response = await api.post(`/forms/${formId}/tokens`, { expiresInMinutes });
    return response.data;
};

export const getTokens = async (formId) => {
    const response = await api.get(`/forms/${formId}/tokens`);
    return response.data;
};

// Public API (Respondent)
export const validateToken = async (publicLink, tokenValue) => {
    const params = new URLSearchParams();
    params.append('token', tokenValue);
    params.append('publicLink', publicLink);

    const response = await api.post('/login/ott', params, {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
    return response.data;
};
