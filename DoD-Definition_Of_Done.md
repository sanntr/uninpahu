# 🟣 Definition of Done – DoD General del Proyecto MARKETPLACE UNINPAHU

El trabajo se considera **terminado** solo cuando cumple TODOS los siguientes puntos:

## ✔ 1. Análisis y planificación
- La historia de usuario está correctamente asociada a una **épica**.
- Cuenta con **subtareas claras** asignadas a los responsables correctos.
- Los criterios de aceptación están definidos y aprobados por el equipo.

## ✔ 2. Desarrollo
- Todo el código fue implementado siguiendo buenas prácticas.
- No existen errores en consola (frontend o backend).
- No hay código muerto, duplicado o comentarios innecesarios.
- El código cumple el estilo acordado por el equipo (ESLint / estándares Java).
- La funcionalidad implementada cubre completamente lo descrito en la HU.

## ✔ 3. Versionamiento
- El código está en el repositorio correspondiente (frontend o backend).
- Todos los commits están limpios, claros y descriptivos.
- El código está integrado correctamente en las ramas del proyecto.

## ✔ 4. Pruebas
- Se verificaron los criterios de aceptación de la historia.
- El QA realizó pruebas funcionales y registró hallazgos.
- Los hallazgos fueron corregidos y se cargó evidencia.
- El flujo completo fue probado (login, navegación, APIs, BD, etc.).

## ✔ 5. Documentación
- README actualizado (frontend y backend).
- Diagramas mermaid generados.
- Evidencias en Jira adjuntadas (capturas, videos, excel, etc.).
- Base de datos documentada y validada (ERD, scripts, formularios).

## ✔ 6. Revisión final
- La subtarea cambia a **FINALIZADA**.
- La HU cambia a **FINALIZADA**.
- La ÉPICA avanza en porcentaje de progreso.
- El equipo valida que es **desplegable** en cualquier momento.

# 🟢 Definition of Done Técnico – Backend + Frontend

## 🟦 A. Backend (Spring Boot)
- Proyecto compila sin errores.
- Todas las entidades, repositorios y servicios están implementados.
- APIs cumplen con los contratos definidos.
- Respuestas HTTP correctas (códigos 200, 400, 401, 404, 500).
- JWT funcionando correctamente: login, registro y autorización.
- Base de datos sincronizada (migraciones Flyway OK).
- Controladores funcionando y probados desde Postman.
- Excepciones personalizadas implementadas.
- Configuraciones en `application.properties` verificadas.
- Pruebas funcionales realizadas por QA (archivo Excel cargado en Jira).
- README del backend actualizado (estructura, APIs, diagramas).

---

## 🟩 B. Frontend (React + Vite)
- Componentes creados según el layout base.
- Diseño responsive verificado en desktop + móvil.
- Ruteo configurado correctamente (React Router).
- Login, registro y dashboard funcionales.
- Integración con backend vía Axios (API URL configurable con .env).
- Manejo correcto de tokens con `jwt-decode`.
- Validaciones de formularios implementadas.
- No hay errores en consola del navegador.
- Swiper funcionando para banners y productos destacados.
- La build de producción se genera correctamente (`npm run build`).
- README del frontend actualizado (scripts, estructura, diagramas).

# 🔵 Definition of Done por Historia de Usuario – MARKETPLACE UNINPAHU

Una Historia de Usuario se considera completada cuando:

## ✔ 1. Subtareas completadas
- Todas las subtareas de la HU están en estado **FINALIZADA**.
- Cada subtarea tiene evidencia adecuada cargada en Jira.

## ✔ 2. Criterios de aceptación cumplidos
- Todos los criterios de aceptación definidos están probados y aprobados.

## ✔ 3. Funcionalidad implementada correctamente
- La HU refleja exactamente lo solicitado por el usuario/negocio.
- No existen desviaciones, omisiones o comportamientos inesperados.

## ✔ 4. Verificación técnica
- Frontend y Backend integran correctamente si aplica.
- BD almacena correctamente los datos requeridos si aplica.
- Validaciones funcionan según lo esperado.

## ✔ 5. QA validó la historia
- QA ejecutó pruebas funcionales (HU-67).
- Se registraron hallazgos (HU-68) y fueron solucionados.
- QA cargó evidencia final (Word + Excel) (HU-69).

## ✔ 6. Documentación vinculada
- Capturas, videos, archivos, pruebas y resultados están adjuntos en Jira.
- La HU queda con estado **FINALIZADA** y marcada como completada.

# 🟠 Definition of Done – Versión Formal para Entrega Académica

## 📌 1. Entregables completos
- Frontend terminado e implementado.
- Backend terminado e implementado.
- Base de datos diseñada, creada, probada y documentada.
- QA completó pruebas y cargó evidencias.
- Todo el código subido al repositorio.

## 📌 2. Documentación requerida
- Documentación técnica (frontend + backend).
- Manual de usuario (si aplica).
- Diagramas UML/Mermaid:
  - Diagrama de clases
  - Flujo del frontend
  - Arquitectura del sistema
- README completos en ambos repositorios.

## 📌 3. Evidencias de trabajo colaborativo
- Épicas, Historias de Usuario y Subtareas correctamente gestionadas en Jira.
- Capturas de progreso, resultados, evidencias adjuntas.

## 📌 4. Calidad del software
- Código sin errores.
- Arquitectura clara.
- Buenas prácticas aplicadas.
- Funcionalidad completa y navegable.

## 📌 5. Entrega final
- Proyecto desplegable localmente sin fallas.
- Fecha de entrega cumplida.
- Revisión final del equipo docente o evaluador.

