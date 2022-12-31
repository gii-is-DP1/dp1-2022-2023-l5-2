# Enlaces Importantes

- URL al video de explicación del juego: https://youtu.be/c3Xa6o98hKk

- Link a el informe de las horas de trabajo de cada alumno en el proyecto: 
https://uses0-my.sharepoint.com/:w:/g/personal/ignarrman_alum_us_es/EZ8eyKLXqZ5CtjhVFQBg0MUBSakloz3gvZRHE4L_vSj24g?e=hRxZqH

# Participantes del Proyecto

- Ignacio Arroyo Mantero
- Tadeo Cabrera Gómez
- Ignacio González González
- Francisco de Asís Rosso Ramírez
- Jesús Solis Ortega
- Elena Tomás Vela

# Descripción del Juego

Boss Monster se trata de un juego estratégico de cartas de dos hasta cuatro jugadores, con partidas de una duración aproximada a 30 minutos, en el que el objetivo es construir una mazmorra que permita atraer y derrotar a la mayor cantidad de héroes posibles. 

En este juego hay cuatro tipos de cartas: 

- **Héroes**: Estas cartas representan a los héroes que entrarán en las mazmorras de los jugadores. Tienen un tesoro preferido y un número que representa su vida. Existen dos tipos de héroes, épicos y no épicos.  

- **Salas/Habitaciones**: Estas cartas representan cada una de las habitaciones de las mazmorras de cada jugador. Cada mazmorra puede tener un máximo de 5. Poseen uno o varios tesoros y dañan a los héroes que pasan por ellas, además de tener distintas habilidades pasivas. Algunas de las cartas de habitación son *avanzadas*. Estas cartas, más poderosas que las normales, solo pueden construirse sobre otra habitación construida del mismo tipo *(Monstruo o Trampa)*.

- **Hechizos**: Estas cartas causan efectos especiales que los jugadores pueden activar al usar la cartas. Sin embargo, solo se pueden usar en momentos específicos del juego.

- **Monstruos finales**: Estas cartas  representan al personaje de cada jugador, y se colocan al final de la mazmorra de cada uno. Tienen un tesoro y cantidad de experiencia asignados, además de un efecto que se activa cuando se construyen todas las habitaciones de la mazmorra por primera vez. A diferencia de las cartas de habitación, los monstruos finales no dañan a los héroes.

Al comienzo de la partida, a cada jugador se le asignará un monstruo final aleatorio y se repartirán tres cartas de habitación y dos de hechizo, de las cuales se deberán descartarán dos cualesquiera. Además, colocarán una sola carta de habitación al lado de su monstruo final boca abajo, revelándolas una vez todos hayan colocado la suya.

Tras el comienzo de la partida, cada turno se divide en varias fases: 

- **Comienzo de turno**: Se revelan tantos héroes como jugadores haya, y se colocan en la *Ciudad*, una pila de cartas especial donde se colocan los héroes que van a entrar en las mazmorras ese turno, donde permanecerán hasta la fase de Señuelo. Cada jugador roba una carta de Sala.  

- **Construcción**: En orden de mayor a menor experiencia de su monstruo final, cada jugador tendrá la oportunidad de construir una nueva habitación en su mazmorra o sustuir una existente por otra nueva. Las cartas de las habitaciones construidas se colocarán boca abajo. Cuando todos los jugadores hayan terminado, se revelarán las habitaciones construidas.

- **Señuelo**: Cada héroe que esté en la *ciudad* irá a la mazmorra que tenga más cantidad de su tesoro preferido. En caso de empate, el héroe se quedará en la ciudad. 

- **Aventura**: Los héroes recorrerán una de las salas de la mazmorra que escogieron, desde la izquierda hacia la derecha, recibiendo el daño y activando el efecto de cada sala en caso de tenerlo.  

Si un héroe sobrevive a las salas de la mazmorra y llega al jefe final, se te aplicarán las heridas que especifique el héroe. (1 si no es épico y 2 si lo es) Los jugadores serán eliminados de la partida si llegan a tener cinco heridas. 
Si el daño de las salas sobrepasa la vida del héroe, el dueño de la mazmorra ganará las almas que tenía (1 si no es épico y 2 si lo es). 

El juego termina cuando un jugador arrebata diez almas de héroes o cuando quede un solo jugador sin eliminar, en cuyo caso ganará dicho jugador. 

En caso de que varios jugadores alcancen diez almas o no quede ningún jugador sin eliminar en el mismo turno, la victoria se concederá al monstruo con menor experiencia.

# Contenido del Proyecto

## Módulo de Juego

### Crear una Nueva Partida

En nuestro proyecto, tanto los usuarios normales como los administradores pueden participar en juegos.

Para crear una partida, un usuario registrado debe pulsar el botón "Create Game" de la pantalla principal. Una vez dentro, el jugador podrá elegir el máximo de jugadores que tendrá la partida. Una vez creada, se redirigirá al usuario a un lobby donde podrá esperar a más jugadores, donde saldrá el código de juego necesario para que los demás usuarios se unan.

Para que otros usuarios se unan a una partida creada deben pulsar el botón "Join Game" de la pantalla principal, donde el sistema les pedirá el código de juego y si quieren unirse a la partida como jugadores o ser solo espectadores de esta sin participar.

Para empezar la partida, el jugador que creó la partida debe pulsar el botón "Start Game", y que al menos hayan dos jugadores unidos.

### Jugar Partida

Ver sección DESCRIPCIÓN DEL JUEGO

### Visualizar el Listado de Partidas Creadas y Jugadas

Para ver las partidas en la que un usuario ha participado, solo hay que pulsar el botón "Statistics" de la pantalla de inicio.

### Visualizar Listado de Partidas en Curso [Exclusivo de Admin]

TODO

### Visualizar Listado de Partidas Jugadas [Exclusivo de Admin]

TODO

## Módulo de Gestión de Usuarios

### Iniciar Sesión / Cerrar Sesión / Crear Cuenta

Para iniciar sesión, cerrar sesión y crear cuenta, hemos usado el código proporcionado por la plantilla de PetClinic proporcionada por Spring.

### Edición de Perfil Personal

Para que un usuario edite su cuenta, debe pulsar el botón "User Management" en la pantalla principal, el cuál le llevará a una pantalla donde podrá cambiar sus datos de usuario. Al intentar guardar los cambios, el sistema comprobará que los datos proporcionados son válidos (El email es uno de verdad, el nombre de usuario es único, la contraseña tiene de 6 a 20 carácteres, etc), y si no cumple los requisitos del sistema, se le informará al usuario y los datos no se guardarán.

### Listado de Usuarios Registrados [Exclusivo de Admin]

Para que un administrador pueda ver el listado de usuarios registrados, debe pulsar el botón "Admin Options" de la pantalla de inicio (Sólo aparece si el usuario registrado es un administrador), donde aparecerá el listado de usuarios registrados en el sistema.

### CRUD de Usuarios [Exclusivo de Admin]

Para que un administrador pueda realizar operaciones CRUD sobre usuarios registrados, debe pulsar el botón "Admin Options" de la pantalla de inicio (Sólo aparece si el usuario registrado es un administrador), donde aparecerá el listado de usuarios registrados en el sistema. Una vez allí puede editar o eliminar usuarios.

### Auditoría de los datos [Exclusivo de Admin]

TODO

## Módulo de Estadística

### Número de Partidas

TODO

### Duración de las Partidas

TODO

### Número de Jugadores por Partida

TODO

### Estadísticas del Juego

TODO

### Ranking de Jugadores

TODO

### Logros en el Perfíl de Usuario

Para ver los logros de un usuario, debe pulsar el botón "My Achievements" en la pantalla inicial, que llevará a una pantalla con un listado de logros del juego, clasificados en obtenidos o no por el jugador.

### Edición de Logros

TODO

## Módulo Social

TODO

### Gestión de Amistades entre Usuarios

TODO

### Invitaciones a Partidas

TODO

### Modo Espectador

TODO

### Chat Público

TODO