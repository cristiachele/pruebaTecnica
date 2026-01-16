import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import './Layout.css';

const Layout = ({ children }) => {
  const location = useLocation();

  const isActive = (path) => {
    return location.pathname === path ? 'active' : '';
  };

  return (
    <div className="layout">
      <aside className="sidebar">
        <h1 className="sidebar-title">BANCO</h1>
        <nav className="sidebar-nav">
          <Link to="/clientes" className={`nav-link ${isActive('/clientes') || isActive('/') ? 'active' : ''}`}>
            Clientes
          </Link>
          <Link to="/cuentas" className={`nav-link ${isActive('/cuentas') ? 'active' : ''}`}>
            Cuentas
          </Link>
          <Link to="/movimientos" className={`nav-link ${isActive('/movimientos') ? 'active' : ''}`}>
            Movimientos
          </Link>
          <Link to="/reportes" className={`nav-link ${isActive('/reportes') ? 'active' : ''}`}>
            Reportes
          </Link>
        </nav>
      </aside>
      <main className="main-content">
        {children}
      </main>
    </div>
  );
};

export default Layout;
