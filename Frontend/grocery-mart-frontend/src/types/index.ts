
export interface Product {
  id: string;
  name: string;
  price: number;
  image: string;
  category: string;
  inStock: boolean;
  description: string;
}

export interface CartItem extends Product {
  product?: Product;
  quantity: number;
}

export interface User {
  id: string;
  email: string;
  name: string;
  cart ?: Cart;
  groceryOrders?: Order[];
}

export interface Order {
  id: string;
  items: CartItem[];
  totalPrice: number;
  discountCode?: string;
  discountAmount?: number;
  deliveryOption: DeliveryOption;
  createdAt: Date;
  address:string;
}

export interface DeliveryOption {
  id: string;
  name: string;
  price: number;
  estimatedTime: string;
}

export interface Cart{
  id: string;
  cartItems: CartItem[];
}
