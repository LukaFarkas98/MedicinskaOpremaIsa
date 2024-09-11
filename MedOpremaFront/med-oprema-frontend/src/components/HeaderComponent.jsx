import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import { AuthContext } from '../authentication/AuthProvider';

const HeaderComponent = () => {
  const { auth, logout } = useContext(AuthContext);

  return (
    <header>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container">
          <Link className="navbar-brand" to="/">User Management</Link>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link className="nav-link" to="/">Home</Link>
              </li>

              {auth.isAuthenticated && auth.user && auth.user.userRole === 'USER' && (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/appointments">My Appointments</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/penalpoints">Penal Points</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/complaints">Complaints</Link>
                  </li>
                </>
              )}

              {auth.isAuthenticated && auth.user && auth.user.userRole === 'SUPER_ADMIN' && (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/admin/complaints-list">Manage Complaints</Link>
                  </li>
                </>
              )}

              {!auth.isAuthenticated && (
                <>
                  <li className="nav-item">
                    <Link className="nav-link" to="/login">Login</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/register">Register</Link>
                  </li>
                </>
              )}

              {auth.isAuthenticated && (
                <li className="nav-item">
                  <button
                    className="nav-link btn btn-link"
                    onClick={() => {
                      logout(); // Call logout function
                    }}
                  >
                    Logout
                  </button>
                </li>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default HeaderComponent;
