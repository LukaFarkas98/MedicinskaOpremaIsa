import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import AuthProvider from './authentication/AuthProvider';



ReactDOM.createRoot(document.getElementById('root')).render(

    <AuthProvider>
      <App />
    </AuthProvider>,

)
