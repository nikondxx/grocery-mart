
import { useState } from 'react';
import { ArrowLeft } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import {CartItem, DeliveryOption, User} from '../types';
import {ApiService} from "@/service/ApiService.ts";

interface CheckoutProps {
  cartItems: CartItem[];
  totalPrice: number;
  user:User;
  onOrderPlaced: (deliveryOption:DeliveryOption, formData: {
    firstName: string;
    lastName: string;
    address: string;
    city: string;
    zipCode: string;
  },discountCode:string,discountAmount:number,subTotal:number) => void;
  onBack: () => void;
}

const deliveryOptions: DeliveryOption[] = [
  {
    id: '1',
    name: 'Standard Delivery',
    price: 5.99,
    estimatedTime: '2-3 business days'
  },
  {
    id: '2',
    name: 'Express Delivery',
    price: 12.99,
    estimatedTime: 'Next business day'
  },
  {
    id: '3',
    name: 'Same Day Delivery',
    price: 19.99,
    estimatedTime: 'Within 4-6 hours'
  }
];

const discountCodes = {
  'SAVE10': 10,
  'FIRST20': 20,
  'WELCOME15': 15
};

const Checkout: React.FC<CheckoutProps> = ({
  cartItems,
  user,
  totalPrice,
  onOrderPlaced,
  onBack,
}) => {
  const [selectedDelivery, setSelectedDelivery] = useState<DeliveryOption>(deliveryOptions[0]);
  const [discountCode, setDiscountCode] = useState('');
  const [appliedDiscount, setAppliedDiscount] = useState(0);
  const [discountError, setDiscountError] = useState('');
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    address: '',
    city: '',
    zipCode: '',
  });

  const applyDiscountCode = async () => {
    const discount = discountCodes[discountCode.toUpperCase() as keyof typeof discountCodes];
    const canApply = await ApiService.canApplyDiscount({discount:discountCode,userId:user.id});

    if (discount && canApply) {
      setAppliedDiscount(discount);
      setDiscountError('');
    } else {
      setDiscountError('Invalid discount code');
      setAppliedDiscount(0);
    }
  };

  const subtotal = totalPrice;
  const tax = totalPrice * 0.0825
  const discountAmount = (subtotal * appliedDiscount) / 100;
  const deliveryFee = selectedDelivery.price;
  const finalTotal = subtotal - discountAmount + deliveryFee + tax;

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-6">
        <Button variant="ghost" onClick={onBack} className="mb-4">
          <ArrowLeft className="w-4 h-4 mr-2" />
          Back to Cart
        </Button>
        <h1 className="text-3xl font-bold">Checkout</h1>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 space-y-6">
          <Card>
            <CardHeader>
              <CardTitle>Delivery Information</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="firstName">First Name</Label>
                  <Input
                      id="firstName"
                      name="firstName"
                      placeholder="John"
                      onChange={(e)=>setFormData({ ...formData, [e.target.name]: e.target.value })}
                  />
                </div>
                <div>
                  <Label htmlFor="lastName">Last Name</Label>
                  <Input
                      id="lastName"
                      name="lasttName"
                      placeholder="Doe"
                      onChange={(e)=>setFormData({ ...formData, [e.target.name]: e.target.value })}
                  />
                </div>
              </div>
              
              <div>
                <Label htmlFor="address">Address</Label>
                <Input
                    id="address"
                    name="address"
                    placeholder="123 Main Street"
                    onChange={(e)=>setFormData({ ...formData, [e.target.name]: e.target.value })}
                />
              </div>
              
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="city">City</Label>
                  <Input
                      id="city"
                      name="city"
                      placeholder="New York"
                      onChange={(e)=>setFormData({ ...formData, [e.target.name]: e.target.value })}
                  />
                </div>
                <div>
                  <Label htmlFor="zipCode">ZIP Code</Label>
                  <Input
                      id="zipCode"
                      name="zipCode"
                      placeholder="10001"
                      onChange={(e)=>setFormData({ ...formData, [e.target.name]: e.target.value })}
                  />
                </div>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Delivery Options</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {deliveryOptions.map((option) => (
                  <div
                    key={option.id}
                    className={`p-4 border rounded-lg cursor-pointer transition-colors ${
                      selectedDelivery.id === option.id
                        ? 'border-green-500 bg-green-50'
                        : 'border-gray-200 hover:border-gray-300'
                    }`}
                    onClick={() => setSelectedDelivery(option)}
                  >
                    <div className="flex justify-between items-center">
                      <div>
                        <p className="font-semibold">{option.name}</p>
                        <p className="text-sm text-gray-600">{option.estimatedTime}</p>
                      </div>
                      <p className="font-semibold">${option.price.toFixed(2)}</p>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardHeader>
              <CardTitle>Discount Code</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="flex space-x-2">
                <Input
                  value={discountCode}
                  onChange={(e) => setDiscountCode(e.target.value)}
                  placeholder="Enter discount code"
                />
                <Button onClick={applyDiscountCode} variant="outline">
                  Apply
                </Button>
              </div>
              {discountError && (
                <p className="text-red-500 text-sm mt-2">{discountError}</p>
              )}
              {appliedDiscount > 0 && (
                <p className="text-green-600 text-sm mt-2">
                  {appliedDiscount}% discount applied!
                </p>
              )}
              <div className="mt-4 text-sm text-gray-600">
                <p>Try these codes: SAVE10, FIRST20, WELCOME15</p>
              </div>
            </CardContent>
          </Card>
        </div>

        <div>
          <Card>
            <CardHeader>
              <CardTitle>Order Summary</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="space-y-2">
                  {cartItems.map((item) => (
                    <div key={item.id} className="flex justify-between text-sm">
                      <span>{item.name} × {item.quantity}</span>
                      <span>${(item.price * item.quantity).toFixed(2)}</span>
                    </div>
                  ))}
                </div>

                <div className="border-t pt-4 space-y-2">
                  <div className="flex justify-between">
                    <span>Subtotal</span>
                    <span>${subtotal.toFixed(2)}</span>
                  </div>

                  {appliedDiscount > 0 && (
                      <div className="flex justify-between text-green-600">
                        <span>Discount ({appliedDiscount}%)</span>
                        <span>-${discountAmount.toFixed(2)}</span>
                      </div>
                  )}

                  <div className="flex justify-between">
                    <span>Tax</span>
                    <span>${tax.toFixed(2)}</span>
                  </div>

                  <div className="flex justify-between">
                    <span>Delivery</span>
                    <span>${deliveryFee.toFixed(2)}</span>
                  </div>

                  <div className="border-t pt-2">
                    <div className="flex justify-between font-bold text-lg">
                      <span>Total</span>
                      <span>${finalTotal.toFixed(2)}</span>
                    </div>
                  </div>
                </div>

                <Button
                    onClick={() => onOrderPlaced(selectedDelivery, formData, discountCode,discountAmount, finalTotal)}
                    className="w-full bg-green-600 hover:bg-green-700 mt-6"
                >
                  Place Order
                </Button>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Checkout;
