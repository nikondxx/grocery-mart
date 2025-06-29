import axios from 'axios';
import {Cart, Order, Product, User} from "@/types";


export interface RegisterRequest {
    email: string;
    password: string;
    username: string;
}

export interface RegisterResponse {
    id: string;
    email: string;
    username: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    email: string;
    password: string;
}

export interface OrderRequest extends Order {
    userId: string;
}

// Create axios instance with base configuration
const api = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
});


api.interceptors.response.use(
    (response) => response,
    (error) => {
        let message = error.response?.data?.message || error.message || 'Request failed';
        if (error.response && error.response.status === 409) {
            message = "Email Already Exists";
        }
        if (error.response && error.response.status === 401) {
            message = "You are not authorized"
        }
        throw new Error(message);
    }
);

export class ApiService {
    static async register(data: RegisterRequest): Promise<User> {
        const response = await api.post('/auth/register', data);
        return response.data;
    }

    static async login(data: LoginRequest): Promise<User> {
        const response = await api.post('/auth/login', data);
        return response.data;
    }

    static async getProducts(): Promise<Product[]> {
        const response = await api.get('/products');
        return response.data;
    }

    static async saveCart(data:Cart): Promise<boolean> {
        const response = await api.post('/cart/save', data);
        return response.data;
    }

    static async saveOrder(order:OrderRequest): Promise<Order> {
        const response = await api.post('/order/save', order);
        return response.data;
    }

    static async canApplyDiscount(data:{discount: string,userId:string}): Promise<boolean> {
        const response = await api.get('/auth/can-apply-discount', {params:data});
        return response.data;
    }
}