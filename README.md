# SistemadeGestion_MaquinasyMontajes

# 🛠️ Sistema de Gestión de Mantenimiento - Máquinas y Montajes S.A.S.

Este proyecto consiste en el desarrollo de un software de escritorio en Java para gestionar el mantenimiento preventivo y correctivo de equipos de izaje (como grúas telescópicas) en la empresa Máquinas y Montajes S.A.S.

## 📌 Objetivo

Optimizar el registro, programación y análisis de mantenimientos mediante un sistema computarizado que reemplace los procesos manuales actuales, mejorando la eficiencia operativa y reduciendo los tiempos de inactividad.

---

## 📂 Estructura del proyecto

sistema-mantenimiento/
├── src/
│ └── com/manteniopro/
│ ├── Manteniopro.java
│ ├── componentes/
│ ├── model/
│ ├── modelos/
│ ├── util/
│ └── vistas/
├── README.md
├── .gitignore


---

## 🧠 Funcionalidades principales

- ✅ Registro de fallas de equipos.
- 🗓️ Programación de mantenimientos preventivos y correctivos.
- 📝 Generación automática de órdenes de trabajo.
- 📊 Reportes e historial de mantenimientos.
- 👤 Gestión de usuarios por roles (Administrador / Trabajador).
- 📁 Persistencia de datos usando archivos `.dat`.

---

## 💻 Tecnologías utilizadas

- **Lenguaje**: Java SE
- **Interfaz gráfica**: Swing
- **Persistencia**: Serialización con archivos binarios (`Serializable`)
- **IDE sugerido**: NetBeans / Eclipse

---

## 📸 Capturas de Pantalla

### 🔐 Inicio de sesión
![Inicio de sesión](Inicio%20de%20sesion.jpg)

### 🛡️ Vista del Administrador
![Vista Admin](Vista%20del%20administrador.png)

### 👷 Vista del Trabajador
![Vista Trabajador](Vista%20del%20Trabajador.png)

---
Aunque el sistema no incluye inteligencia artificial aún, la **estructura de datos que maneja (usuarios, tareas, equipos, reportes)** permite su futura explotación con herramientas de ciencia de datos.

### Datos recopilados:
- Tiempos de mantenimiento por equipo
- Historial de fallas
- Frecuencia de tareas asignadas por trabajador
- Capacidad y modelo de los equipos

### Potenciales análisis:
- 📈 **Detección de equipos con más fallas** → para planificación predictiva
- 📅 **Identificación de ciclos de mantenimiento** por uso
- 👷‍♂️ **Carga de trabajo por operario**
- 📉 **Disminución de fallos después de mantenimientos preventivos**


---

## 👨‍💻 Autores

- Oscar Tejedor González  
- Luis Goez  


---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Puedes copiar, modificar y distribuir este software con fines académicos o personales.

---

## 📬 Contacto

Para más información o colaboración, contacta a:  
📧 odtg91@gmail.com  
