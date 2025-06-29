import { useState, useEffect } from 'react';
import Header from '../components/Header';
import ProductGrid from '../components/ProductGrid';
import Cart from '../components/Cart';
import Checkout from '../components/Checkout';
import AuthModal from '../components/AuthModal';
import OrderHistory from '../components/OrderHistory';
import {Product, CartItem, User, Order, Cart as CartObj, DeliveryOption} from '../types';
import {ApiService} from "@/service/ApiService.ts";

const Index = () => {
  const [user, setUser] = useState<User | null>(null);
  const [showAuthModal, setShowAuthModal] = useState(false);
  const [authMode, setAuthMode] = useState<'login' | 'register'>('login');
  const [products, setProducts] = useState<Product[]>([]);
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [showCart, setShowCart] = useState(false);
  const [showCheckout, setShowCheckout] = useState(false);
  const [showOrderHistory, setShowOrderHistory] = useState(false);
  const [orders, setOrders] = useState<Order[]>([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [sortBy, setSortBy] = useState<'name' | 'price' | 'availability'>('name');
  const [sortOrder, setSortOrder] = useState<'asc' | 'desc'>('asc');


  useEffect(() => {
    ApiService.getProducts()
        .then(products => setProducts(products))
        .catch(err => alert("Product fetching failed!"));
  }, []);

  // Filter and sort products
  const filteredAndSortedProducts = products
    .filter(product => 
      product.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      product.category.toLowerCase().includes(searchQuery.toLowerCase())
    )
    .sort((a, b) => {
      let comparison = 0;
      
      switch (sortBy) {
        case 'name':
          comparison = a.name.localeCompare(b.name);
          break;
        case 'price':
          comparison = a.price - b.price;
          break;
        case 'availability':
          comparison = (b.inStock ? 1 : 0) - (a.inStock ? 1 : 0);
          break;
      }
      
      return sortOrder === 'asc' ? comparison : -comparison;
    });

  const addToCart = (product: Product) => {
    if (!user) {
      setShowAuthModal(true);
      return;
    }

    const existingItem = cartItems.find(item => item.id === product.id);
    
    if (existingItem) {
      setCartItems(cartItems.map(item =>
        item.id === product.id
          ? { ...item, quantity: item.quantity + 1 }
          : item
      ));
    } else {
      setCartItems([...cartItems, { ...product, quantity: 1 ,price: product.price, product:product }]);
    }
  };

  const removeFromCart = (productId: string) => {
    setCartItems(cartItems.filter(item => item.id !== productId));
  };

  const updateQuantity = (productId: string, quantity: number) => {
    if (quantity <= 0) {
      removeFromCart(productId);
      return;
    }
    
    setCartItems(cartItems.map(item =>
      item.id === productId
        ? { ...item, quantity }
        : item
    ));
  };

  const getTotalItems = () => {
    return cartItems.reduce((total, item) => total + item.quantity, 0);
  };

  const getTotalPrice = () => {
    return cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
  };

  const handleLogin = async (email: string, password: string) => {
  
    try{
      const loggedUser = await ApiService.login({email, password});
      setUser(loggedUser);
      setShowAuthModal(false);
      setCartItems(loggedUser.cart?.cartItems);
      setOrders(loggedUser.groceryOrders);
    }
    catch(e){
      alert(e);
    }
    finally{
      setShowAuthModal(false);
    }
  };

  const handleRegister = async (email: string, password: string, name: string) => {
    try {
      const user = await ApiService.register({email,password,username:name});
      setUser(user);
    }
    catch (error) {
      alert(error);
    }
    finally {
      setShowAuthModal(false);
      setCartItems([]);
      setOrders([])
    }
  };

  

  const handleLogout = () => {
    setUser(null);
    setCartItems([]);
    setOrders([]);
    setShowCart(false);
    setShowCheckout(false);
    setShowOrderHistory(false);
  };

  const handleCheckout = () => {
    setShowCart(false);
    setShowCheckout(true);
  };

  const handleSaveCart = async ()=>{
    const cart : CartObj = {id:user.cart.id,cartItems: cartItems}
    try {
      const isSuccess = await ApiService.saveCart(cart);
      if(isSuccess){
        setShowCart(false);
      }
      else{
        alert("Error occurred while saving the cart!")
      }
    }
    catch (err){
      alert("Error occurred while saving the cart!")
    }

  }

  const handleOnCartClick = ()=>{
    setShowCart(true);
    setShowOrderHistory(false);
  }

  const handleOrderPlaced = async (deliveryOption:DeliveryOption,formData: {
    firstName: string;
    lastName: string;
    address: string;
    city: string;
    zipCode: string;
  }, discountCode:string,discountAmount:number,subTotal:number) => {

    const newOrder: Order = {
      id: null,
      items: [...cartItems],
      totalPrice: subTotal,
      deliveryOption: deliveryOption,
      createdAt: new Date(),
      address: formData.firstName + " " + formData.lastName + " ," + formData.address + " ," + formData.city + " ," + formData.zipCode,
    };
    const savedOrder = await ApiService.saveOrder({...newOrder,userId:user.id,discountCode:discountCode,discountAmount:discountAmount});
    setOrders([savedOrder, ...orders]);
    setCartItems([]);
    setShowCheckout(false);
    alert('Order placed successfully!');
  };

  if (showOrderHistory) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Header
          user={user}
          cartItemsCount={getTotalItems()}
          onCartClick={handleOnCartClick}
          onLoginClick={() => setShowAuthModal(true)}
          onLogout={handleLogout}
          onOrderHistoryClick={() => setShowOrderHistory(true)}
          searchQuery={searchQuery}
          onSearchChange={setSearchQuery}
          sortBy={sortBy}
          onSortChange={setSortBy}
          sortOrder={sortOrder}
          onSortOrderChange={setSortOrder}
        />
        <OrderHistory
          orders={orders}
          onBack={() => setShowOrderHistory(false)}
        />
      </div>
    );
  }

  if (showCheckout) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Header
          user={user}
          cartItemsCount={getTotalItems()}
          onCartClick={() => setShowCheckout(false)}
          onLoginClick={() => setShowAuthModal(true)}
          onLogout={handleLogout}
          onOrderHistoryClick={() => setShowOrderHistory(true)}
          searchQuery={searchQuery}
          onSearchChange={setSearchQuery}
          sortBy={sortBy}
          onSortChange={setSortBy}
          sortOrder={sortOrder}
          onSortOrderChange={setSortOrder}
        />
        <Checkout
          cartItems={cartItems}
          totalPrice={getTotalPrice()}
          onOrderPlaced={handleOrderPlaced}
          onBack={() => setShowCheckout(false)}
          user={user}
        />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Header
        user={user}
        cartItemsCount={getTotalItems()}
        onCartClick={() => setShowCart(true)}
        onLoginClick={() => setShowAuthModal(true)}
        onLogout={handleLogout}
        onOrderHistoryClick={() => setShowOrderHistory(true)}
        searchQuery={searchQuery}
        onSearchChange={setSearchQuery}
        sortBy={sortBy}
        onSortChange={setSortBy}
        sortOrder={sortOrder}
        onSortOrderChange={setSortOrder}
      />

      <main className="container mx-auto px-4 py-8">
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">Fresh Groceries</h1>
          <p className="text-gray-600">Delivered to your doorstep</p>
        </div>

        <ProductGrid
          products={filteredAndSortedProducts}
          onAddToCart={addToCart}
        />
      </main>

      {showCart && (
        <Cart
          items={cartItems}
          onRemove={removeFromCart}
          onUpdateQuantity={updateQuantity}
          onClose={() => setShowCart(false)}
          onCheckout={handleCheckout}
          onSaveCart={handleSaveCart}
          totalPrice={getTotalPrice()}
        />
      )}

      {showAuthModal && (
        <AuthModal
          mode={authMode}
          onClose={() => setShowAuthModal(false)}
          onLogin={handleLogin}
          onRegister={handleRegister}
          onSwitchMode={(mode) => setAuthMode(mode)}
        />
      )}
    </div>
  );
};

export default Index;
