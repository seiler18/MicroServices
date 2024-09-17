# 🌐 Microservices Project - RAMA DEVELOP

Bienvenido al **Microservices Project**, una colección de servicios diseñados para ofrecer una solución completa de gestión de productos, clientes y facturas en una tienda. Este proyecto está dividido en tres microservicios principales:

- **Product Service**: Gestión de productos en la tienda.
- **Customer Service**: Mantenimiento de clientes y sus ubicaciones.
- **Shopping Service**: Gestión de facturas y compras.

Cada servicio está desarrollado utilizando **Spring Boot** y **Spring Cloud** para crear una arquitectura escalable y eficiente basada en microservicios.

![Spring Boot Logo](https://img.shields.io/badge/Spring%20Boot-v3.3.3-brightgreen) ![Java](https://img.shields.io/badge/Java-17-orange) ![Microservices](https://img.shields.io/badge/Microservices-Cloud-blue)

---

## 📂 Servicios

### 🛒 Product Service

Un microservicio para gestionar productos dentro de una tienda, incluyendo la creación, actualización y eliminación de productos.

- [Product Service README.md](./product-service/README.md)

### 👥 Customer Service

Un microservicio para el mantenimiento de clientes y sus ubicaciones, separados por apellido y regiones.

- [Customer Service README.md](./customer-service/README.md)

### 🧾 Shopping Service

Un microservicio para gestionar las facturas y compras, proporcionando funcionalidades para crear y mantener registros de facturas.

- [Shopping Service README.md](./shopping-service/README.md)

---

## 📚 Documentación y Guías

Para más información sobre las tecnologías y conceptos utilizados en este proyecto, consulta los siguientes enlaces:

- [Documentación oficial de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación oficial de Spring Cloud](https://spring.io/projects/spring-cloud)
- [Documentación de Microservicios](https://microservices.io/)

---

## 🚀 Getting Started

Para comenzar con el proyecto, sigue estos pasos:

1. Clona el repositorio:

    ```bash
    git clone https://github.com/seiler18/MicroservicesProject.git
    ```

2. Navega a la carpeta del servicio que desees iniciar:

    ```bash
    cd product-service
    ./gradlew bootRun
    ```

    ```bash
    cd customer-service
    ./gradlew bootRun
    ```

    ```bash
    cd shopping-service
    ./gradlew bootRun
    ```

3. Asegúrate de tener las bases de datos necesarias configuradas para cada servicio, como PostgreSQL o MySQL.

---

## 🔧 Tecnologías Utilizadas

- **Spring Boot**: Framework para construir aplicaciones Java.
- **Spring Cloud**: Herramientas para microservicios y arquitectura distribuida.
- **Eureka**: Servicio de descubrimiento.
- **Spring Data JPA**: Acceso a datos.

---

## 📝 Additional Links

Links adicionales sobre el autor y su trabajo:

- [Mi curriculo Jesús Seiler](https://seiler18.github.io/Curriculo/)
- [Mi repositorio de proyectos](https://github.com/seiler18)

---

## 👨‍💻 Autor

Este proyecto ha sido creado por **Jesús Seiler**. ¡Siéntete libre de contribuir!

![Contributions welcome](https://img.shields.io/badge/Contributions-Welcome-brightgreen)

---

## ⚖️ License

Este proyecto está bajo la licencia **©Jesús Seiler 2024. Todos los derechos reservados.**.
