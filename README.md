# API REST - CRUD de Producto (Java Spring Boot)

Este proyecto fue desarrollado como parte de un curso de **Backend con Java y Spring Boot**. Consiste en una **API REST** que permite realizar operaciones CRUD (crear, leer, actualizar y eliminar) sobre una entidad **Producto**.

El proyecto incluye:
- Arquitectura en capas (Controller, Service, Repository).
- Manejo de excepciones personalizadas.
- Validaciones básicas.
- Datos precargados.
- Pruebas de la API usando **Postman**.
- Demostración en [video](https://youtu.be/XlD3Sg38lHA)

---

## 🚀 Tecnologías utilizadas
- **Java 21**
- **Spring Boot** (Web, JPA, Validation, Lombok)
- **H2 Database** Un perfil (es la configuración que se usa por defecto)
- **MySQL Database** Otro perfil (se puede usar también)
- **Maven**
- **Postman** para pruebas

---

## 📦 Entidad: Producto
La API gestiona productos con los siguientes campos:

```json
{
  "id": 1,
  "name": "Laptop",
  "description": "Equipo portátil",
  "price": 1500.0,
  "stock": 10,
  "imageUrl": "http://imagenes.com/laptop.jpg"
}
```

---

## 🔥 Endpoints principales

### 📌 Obtener todos los productos
**GET** `/api/productos`

### 📌 Obtener producto por ID
**GET** `/api/productos/{id}`

### 📌 Crear un nuevo producto
**POST** `/api/productos`
Ejemplo de body:
```json
{
  "name": "Mouse Gamer",
  "description": "Mouse RGB",
  "price": 25.99,
  "stock": 50,
  "imageUrl": "http://imagenes/mouse.jpg"
}
```

### 📌 Actualizar un producto
**PUT** `/api/productos/{id}`

### 📌 Eliminar un producto
**DELETE** `/api/productos/{id}`

---

## ⚠️ Manejo de excepciones
El proyecto utiliza dos excepciones personalizadas:
- **ResourceNotFoundException** → cuando el producto no existe.
- **DuplicateResourceException** → cuando se intenta crear un producto duplicado.

Utiliza un excepción de Spring Boot
- **MethodArgumentNotValidException** → cuando los datos enviados no son válidos.

Todas gestionadas por un manejador global que devuelve respuestas claras al cliente.

---

## 🧪 Pruebas con Postman
Se realizó un video demostrativo probando todos los endpoints de la API usando **Postman**, mostrando:
- Creación de productos
- Listado general
- Búsqueda por ID
- Actualización
- Eliminación
- Comportamiento ante errores

---

## 📚 Datos precargados
Al iniciar la aplicación, se cargan productos por defecto mediante un **DataLoader**, para poder probar la API sin necesidad de ingresar datos manualmente.

---

## ▶️ Cómo ejecutar el proyecto
1. Clonar el repositorio
```bash
git clone https://github.com/tuusuario/tu-repo.git
```
2. Entrar al proyecto
```bash
cd tu-repo
```
3. Ejecutar con Maven
```bash
mvn spring-boot:run
```
4. Abrir en el navegador o Postman
```
http://localhost:8080/api/productos
```

También se puede usar cualquier IDE como Netbeans, IntelliJ o Eclipse

---

## 📄 Licencia
Este proyecto es de uso educativo y puede ser utilizado como referencia para futuros desarrollos.
