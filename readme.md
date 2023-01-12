
![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/titleScreenExample.jpg)

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/gameScreenExample.jpg)

# Enlaces Importantes

- URL al video de explicación de normas del juego: https://youtu.be/c3Xa6o98hKk

- URL al video de demostración de la aplicación: //TODO

# Participantes del Proyecto

- Ignacio Arroyo Mantero
- Tadeo Cabrera Gómez
- Ignacio González González
- Francisco de Asís Rosso Ramírez
- Jesús Solis Ortega
- Elena Tomás Vela

# Descripción del Juego

Boss Monster se trata de un juego estratégico de cartas de dos hasta cuatro jugadores, con partidas de una duración aproximada a 30 minutos, en el que el objetivo es construir una mazmorra que permita atraer y derrotar a la mayor cantidad de héroes posibles sobreviviendo a sus ataques.

En este juego hay cuatro tipos de cartas: 

- **Héroes**: Estas cartas representan a los héroes que entrarán en las mazmorras de los jugadores. Tienen un tesoro preferido y un número que representa su vida. También tienen un número mínimo de jugadores requeridos para que aparezcan. Existen dos tipos de héroes, épicos y normales. Los héroes épicos solo aparecerán cuando no queden cartas de normales.

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/heroCardsExample.jpg)

- **Salas/Habitaciones**: Estas cartas representan cada una de las habitaciones de las mazmorras de cada jugador. Cada mazmorra puede tener un máximo de 5. Poseen uno o varios tesoros y dañan a los héroes que pasan por ellas, además de tener distintas habilidades pasivas. Algunas de las cartas de habitación son *avanzadas*. Estas cartas, más poderosas que las normales, solo pueden construirse sobre otra habitación construida del mismo tipo *(Monstruo o Trampa)*.

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/roomCardsExample.jpg)

- **Hechizos**: Estas cartas causan efectos especiales que los jugadores pueden activar al usar la carta. Sin embargo, solo se pueden usar en momentos específicos del juego, indicado en una esquina inferior de esta. *Martillo: Fase de Construcción, Hacha: Fase de Aventura*

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/spellCardsExample.jpg)

- **Monstruos finales**: Estas cartas  representan al personaje de cada jugador, y se colocan al final de la mazmorra de cada uno. Tienen un tesoro y cantidad de experiencia asignados, además de un efecto que se activa cuando se construyen todas las habitaciones de la mazmorra por primera vez. A diferencia de las cartas de habitación, los monstruos finales no dañan a los héroes.

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/finalBossCardExample.jpg)

Al comienzo de la partida, a cada jugador se le asignará un monstruo final aleatorio y se repartirán tres cartas de habitación y dos de hechizo, de las cuales se deberán descartarán dos cualesquiera. Además, colocarán una sola carta de habitación al lado de su monstruo final boca abajo, revelándolas una vez todos hayan colocado la suya.

Tras el comienzo de la partida, cada turno se divide en varias fases: 

- **Comienzo de turno**: Se revelan tantos héroes como jugadores haya, y se colocan en la *Ciudad*, una pila de cartas especial donde se colocan los héroes que van a entrar en las mazmorras ese turno, donde permanecerán hasta la fase de Señuelo. Cada jugador roba una carta de Sala a no ser que tenga cinco o más cartas en su mano.

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/startRoundExample.jpg)

- **Construcción**: En orden de mayor a menor experiencia de su monstruo final, cada jugador tendrá la oportunidad de construir una nueva habitación en su mazmorra o sustuir una existente por otra nueva. Las cartas de las habitaciones construidas se colocarán boca abajo. Cuando todos los jugadores hayan terminado, se revelarán las habitaciones construidas.

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/buildPhaseExample1.jpg)

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/buildPhaseExample2.jpg)

- **Señuelo**: Cada héroe que esté en la *ciudad* irá a la mazmorra que tenga más cantidad de su tesoro preferido. En caso de empate, el héroe se quedará en la ciudad. 

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/lurePhaseExample.jpg)

- **Aventura**: Los héroes recorrerán una de las salas de la mazmorra que escogieron, desde la derecha hacia la izquierda, recibiendo el daño y activando el efecto de cada sala en caso de tenerlo.  

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/adventurePhaseExample.jpg)

Si un héroe sobrevive a las salas de la mazmorra y llega al jefe final, se te aplicarán las heridas que especifique el héroe. (1 si no es épico y 2 si lo es) Los jugadores serán eliminados de la partida si llegan a tener cinco heridas. 
Si el daño de las salas sobrepasa la vida del héroe, el dueño de la mazmorra ganará las almas que tenía (1 si no es épico y 2 si lo es). 

El juego termina cuando un jugador arrebata diez almas de héroes o cuando quede un solo jugador sin eliminar, en cuyo caso ganará dicho jugador. 

En caso de que varios jugadores alcancen diez almas o no quede ningún jugador sin eliminar en el mismo turno, la victoria se concederá al monstruo con menor experiencia.

![Alt text](https://github.com/gii-is-DP1/dp1-2022-2023-l5-2/blob/master/src/main/resources/static/resources/images/readme/resultScreenExample.jpg)
