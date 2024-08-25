//import React, { useEffect, useState } from 'react';
import React, { useEffect, useState, useContext } from 'react'; // Import useContext
import axios from 'axios';
import './ViewOrders.css'; // Optional: Add your CSS file

// Assuming you have an AuthContext that provides the logged-in user's information
import { AuthContext } from '../Context/AuthContext'; // Adjust the path based on your project structure


const ViewOrders = () => { 
  const { user } = useContext(AuthContext); // Retrieve the current user's data from AuthContext
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (userId) {
      axios.get(`http://localhost:8080/orders/user/${user.id}`)
        .then(response => {
          setOrders(response.data);
          setLoading(false);
        })
        .catch(error => {
          setError(error);
          setLoading(false);
          console.error('Error fetching orders:', error);
        });
    }
  }, [userId]);

  if (loading) {
    return <p>Loading orders...</p>;
  }

  if (error) {
    return <p>Error fetching orders: {error.message}</p>;
  }

  if (orders.length === 0) {
    return <p>No orders found for this user.</p>;
  }

  return (
    <div className="orders-list">
      <h2>Your Orders</h2>
      {orders.map(order => (
        <div className="order-card" key={order.orderId}>
          <p><strong>Order ID:</strong> {order.orderId}</p>
          <p><strong>Status:</strong> {order.status}</p>
          <p><strong>Total Amount:</strong> ${order.totalAmount}</p>
          <p><strong>Order Date:</strong> {new Date(order.orderDate).toLocaleDateString()}</p>
          {/* Add more order details as needed */}
        </div>
      ))}
    </div>
  );
};

export default ViewOrders;
