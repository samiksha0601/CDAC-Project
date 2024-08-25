// src/components/Cart.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function Cart() {
  const { userId } = useParams(); // Ensure userId is retrieved from URL params
  const [cart, setCart] = useState(null); // Set initial state to null to differentiate between loading and empty cart

  useEffect(() => {
    if (userId) { // Check if userId is defined
      axios.get(`http://localhost:8080/carts/${userId}`)
        .then(response => {
          console.log('API Response:', response.data); // Debugging: Inspect API response
          setCart(response.data); // Set the entire cart object in state
        })
        .catch(error => console.error('Error fetching cart items:', error));
    }
  }, [userId]);

  if (!cart) {
    return <div>Loading...</div>; // Loading state
  }

  if (cart.items.length === 0) {
    return <div>Your cart is empty.</div>; // Empty cart state
  }

  return (
    <div>
      <h2>Your Cart</h2>
      <table>
        <thead>
          <tr>
            <th>Cart ID</th>
            <th>Order Quantity</th>
            <th>Book ID</th>
            <th>Book Title</th>
            <th>User ID</th>
            <th>Actions</th>
          </tr>
        </thead>
        
      </table>
    </div>
  );
}

export default Cart;
