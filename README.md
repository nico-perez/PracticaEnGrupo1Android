1ª Práctica a realizar en grupo
-------
Integrantes:
* [Álvaro Silva Bolea](https://github.com/alvarosilvabolea) (jefe de equipo)
* [Manuel Sancho Montañés](https://github.com/ElManu3le)
* [Nicolás Pérez Prades](https://github.com/nico-perez) 


[Nuestro Trello](https://trello.com/b/zoSQyrZy/proyecto-android)

-------
Se pide realizar una aplicación para móviles Android mediante Android Studio
similar a la aplicación Yuka, utilizando las librerías vistas en clase o similares.
Naturalmente no se pide todo lo que hace esta aplicación, si no solo algunas de sus
características que se describen en el apartado Requisitos.

Requisitos
-------
1. Se mostrará una lista como mínimo de 30 productos, que se puedan encontrar en un supermercado. 
La información de los productos, como sus imágenes ya existirán dentro 
del código de la aplicación, es decir no se conectará a Internet para conseguir dicha información.
Cada producto debe tener información acerca de su imagen, designación, su calificación como "Excelente /Bueno /Regular
/Malo /Mediocre", la última vez que fue consultado y nombre del supermercado donde
se puede comprar (solo uno, este dato no se visualiza en la siguiente imagen pero tiene
que estar). Se podrá realizar scroll para visualizarlos todos.

2. Cuando se pulse sobre uno de los productos, se accederá a una siguiente pantalla en
la que se mostrarán los detalles de ingredientes, o/y el valor nutricional (azucar, valor
energético, grasas, etc). Deberán existir iconos que acompañen a las descripciones.
Tenéis un ejemplo en la siguiente imagen. Ayuda: Tenéis que añadir al adapter de la
lista un listener, de modo que al pulsar el usuario sobre un item se detecte y se pueda
lanzar la segunda pantalla. Esto no lo hemos visto en clase así que tenéis que
investigarlo.

3. Existirá un botón flotante FAB en la parte inferior derecha con el icono de un código
de barras, que al ser pulsado se accederá a una tercera pantalla.

4. Trabajo de investigación: Deberéis incluir UN añadido a los requisitos anteriores, por
ejemplo que al pulsar el botón de la linterna se encienda el led del dispositivo, que
funcione la cámara dentro del rectángulo en la pantalla del apartado tercero, que al
pulsar sobre un valor nutricional se lance el navegador con la wikipedia acerca del
mismo, etc. Solo se pide implementar una característica adicional, eso sí no se admite
que varios grupos implementen la misma característica, así que intentar implementar
algo distinto al resto de grupos.
