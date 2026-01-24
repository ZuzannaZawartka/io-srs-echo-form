import api from './axiosConfig';

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
    const response = await api.post('/public/tokens', {
        publicLink,
        tokenValue
    });
    return response.data;
};
