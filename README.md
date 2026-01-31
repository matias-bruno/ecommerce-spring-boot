# 🛒 Backend Ecommerce – Java & Spring Boot

API REST de un ecommerce desarrollada con **Java y Spring Boot**, creada con fines educativos y pensada como backend para una aplicación web o mobile.

---

## ✨ Características

- Arquitectura en **tres capas**:
  - Controllers
  - Services
  - Repositories

- **Autenticación y autorización** mediante **JWT (JSON Web Tokens)**.
- Manejo de **roles de usuario**:
  - `ADMIN`
  - `USER`

- **Gestión de productos**:
  - Los productos pueden ser visualizados por cualquier usuario.
  - Listado de productos con **paginación**.
  - **Búsqueda de productos por nombre**.
  - El administrador puede crear, actualizar y gestionar el inventario.

- **Gestión de pedidos**:
  - Es necesario estar **registrado** para realizar un pedido.
  - Los pedidos se almacenan en el sistema.
  - Cada usuario puede ver **únicamente sus propios pedidos**.
  - El administrador puede ver **todos los pedidos**.

- **Manejo centralizado de errores**:
  - Respuestas claras ante errores de validación, autenticación y recursos no encontrados.

---

## 🛠️ Tecnologías utilizadas

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Base de datos relacional

---

## 🚀 Objetivo del proyecto

Este proyecto fue desarrollado como práctica de backend, aplicando buenas prácticas de arquitectura, seguridad y diseño de APIs REST.
