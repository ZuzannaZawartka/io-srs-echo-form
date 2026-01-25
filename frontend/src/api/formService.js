import api from './axiosConfig';

// Internal API (Creator)
export const createForm = async (title, content) => {
    const response = await api.post('/forms', { title, content });
    return response.data;
};

export const getAllForms = async () => {
    const response = await api.get('/forms');
    return response.data;
};

export const getFormById = async (id) => {
    const response = await api.get(`/forms/${id}`);
    return response.data;
};

export const generatePublicLink = async (id) => {
    const response = await api.post(`/forms/${id}/public-link`);
    return response.data;
};

// Public API (Respondent)
export const getPublicFormMeta = async (publicLink) => {
    const response = await api.get(`/public/forms/${publicLink}/meta`);
    return response.data;
};

export const getPublicForm = async (publicLink) => {
    const response = await api.get(`/public/forms/${publicLink}`);
    return response.data;
};
