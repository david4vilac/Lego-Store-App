Prueba Lego APP
-------------
Repositorio creado con el fin de dar solución a la prueba técnica de Alternova.

NIVEL 1
-------------
Diseñar una pantalla de autenticación que consumirá el servicio de firebase
    - Se hace el consumo del servicio de Firebase. 
    ![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/consumo_firebase.png)

NIVEL 2
-------------
Agregar 2 estilos de tema diferentes a esta pantalla de login (tema libre Ej: Dark y light)
    - Se agregan los temas Dark y Light con persistencia en las diferentes actividades.
    
    - Tema Oscuro
![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/tema_dark.png)

    - Tema Claro
![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/tema_light.png)
    

NIVEL 3
-------------
 Una vez me loguee la sesión debe persistir
    - La aplicación persiste por medio de shared preferences.


NIVEL 4
-------------
 Luego de iniciar sesión deberá mostrar la pantalla principal de la tienda, esta pantalla mostrar una lista de productos que podría obtener del Mock Dummy proporcionado.
 
![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/pantalla_home.png)

NIVEL 5
-------------
En esta pantalla podría ver la foto de cada producto y agregarlos al carrito de acuerdo a su stock.
    - Para ingresar al carro de compra se debe presionar en el vector de la esquina inferior, este lleva la cuenta de los productos que se registren en el momento, los productos se agregan en el carro azul que está al lado de cada producto. 
    
![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/agregar_carrito.png)

NIVEL 6
-------------
Podrá seleccionar un producto y esto lo llevará a una nueva vista con el detalle de estos productos (desde acá también lo podría agregar al carrito)

![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/detalle_item.png)

NIVEL 6
-------------
Finalmente, el usuario podrá dar click en comprar esto enviara una petición al Api y actualizara el stock de los productos (nota el api es un mock dummy siempre tendrá la misma respuesta el objetivo es actualizar el stock con esa respuesta y no con los valores reales de la compra)
    - Al ingresar al apartado del carrito de compra y pulsar el boton *comprar*, la vista principal cambia.
    
  ![](https://github.com/david4vilac/Lego-Store-App/blob/main/Imagenes%20Prueba/carrito_compra.png)

