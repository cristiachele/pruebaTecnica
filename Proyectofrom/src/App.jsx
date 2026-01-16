import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Clientes from './pages/Clientes';
import Cuentas from './pages/Cuentas';
import Movimientos from './pages/Movimientos';
import Reportes from './pages/Reportes';

function App() {
  return (
    <Layout>
      <Routes>
        <Route path="/" element={<Clientes />} />
        <Route path="/clientes" element={<Clientes />} />
        <Route path="/cuentas" element={<Cuentas />} />
        <Route path="/movimientos" element={<Movimientos />} />
        <Route path="/reportes" element={<Reportes />} />
      </Routes>
    </Layout>
  );
}

export default App;
