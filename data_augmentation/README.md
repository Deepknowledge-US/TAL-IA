# Data Augmentation
¿Qué es la *data augmentation* ¿Cómo la hemos utilizado en este proyecto? Las respuestas a esas preguntas y a muchas otras en este documento.

## Fundamentación teórica

Dentro del campo del análisis de datos la *data augmentation*, aumento de datos o expansión de datos, son una serie de técnicas utilizadas para incrementar las dimensiones del conjunto de datos de trabajo considerando, además de los datos originales, copias de los mismos con ligeras modificaciones, o incluso datos nuevos creados a partir de otras entradas, los llamados datos sintéticos. Esta herramienta, además de suplir en parte los problemas asociados a conjuntos de datos de tamaño reducido, nos permite reducir el sobreajuste, problema común en los modelos considerados.

## Implementación

En el paquete [Video augmentation in Python](https://github.com/RodGal-2020/video_augmentation) hemos desarrollado una serie de transformaciones capaces de modificar los datos lo suficiente como para que diferentes modelos de aprendizaje profundo mejoren su rendimiento recurriendo a estos vídeos. Con el paquete mencionado es posible lanzar un experimento como el siguiente:

```python
import video_augmentation_functions as va
va.set_seed(1974) # Garantizamos la reproducibilidad del experimento recurriendo a una semilla

## Carpetas de trabajo
my_input_dir = "data/"
my_input_format = "mp4"

my_output_format = "mp4" # También admite "avi"
my_output_dir = "new_data/augment/"

my_log_dir = "logs/augment.log"

## Valores booleanos
my_save_video = True # Queremos salvar el vídeo en output_dir?
my_show_video = True # Queremos mostrar el vídeo?
my_show_size = True # Queremos mostrar las dimensiones del vídeo en el título?
my_slow = False # Queremos reproducir el vídeo a la velocidad real o a la que lo lee el paquete opencv?
my_debug_mode = False # Queremos mostrar información adicional, útil para el depurado?

## Otros parámetros
my_seconds_before_action = 0 # Segundos antes de empezar a ver el vídeo, útil si queremos omitir algunos elementos
my_noise_prob = 0.1 # Probabilidad de añadir ruido al vídeo (solo para las transformaciones de sal y pimienta)
my_transformations = ["aff", "bpepper", "bsalt", "blur", "mblur", "apepper", "asalt", "dsample-0.1", "usample-0.2"] # Estas son todas las transformaciones posibles actualmente

### Ejecución
va.augment(
    input_dir = my_input_dir, 
    output_dir = my_output_dir, 
    input_format = my_input_format, 
    output_format = my_output_format, 
    show_video = my_show_video, 
    save_video = my_save_video, 
    slow = my_slow, 
    show_size = my_show_size, 
    seconds_before_action = my_seconds_before_action, 
    transformations = my_transformations, 
    noise_prob = my_noise_prob,
    debug_mode = my_debug_mode,
    log_dir = my_log_dir)
```

Lanzando el código anterior, a partir de la carpeta `my_input_dir = "data"` y de los archivos `mp4` en ella generaríamos una nueva carpeta `my_output_dir = "new_data/augment/"`, en la que aparecerían los vídeos de carpeta de entrada transformados según las órdenes incluidas en `my_transformations`. Para mostrar un ejemplo partiremos de la palabra "Álgebra", del conjunto de datos SACU:

<p align='center'><img src='new_data/gif/1/original_Algebra.gif' width='30%'><br><sup>Vídeo original procesado para la palabra "Álgebra".</sup><br></p>

Podemos ver el aspecto de cada una de estas transformaciones aplicadas al vídeo anterior a continuación, aunque hay que tener en cuenta que con la orden arriba indicada se generaría, para este vídeo concreto, otro aplicándole **todas** las transformaciones:

<p align='center'><img src='new_data/gif/1/aff_Algebra.gif' width='30%'><img src='new_data/gif/1/apepper_Algebra.gif' width='30%'><img src='new_data/gif/1/bsalt_Algebra.gif' width='30%'><br><sup>De izquierda a derecha: afinidad, pimienta después, sal después.</sup><br></p>
<p align='center'><img src='new_data/gif/1/blur_Algebra.gif' width='30%'><img src='new_data/gif/1/bsalt_Algebra.gif' width='30%'><img src='new_data/gif/1/dsample-0.1_Algebra.gif' width='30%'><br><sup>De izquierda a derecha: difuminado, sal antes, submuestreo (aceleración) con probabilidad 0.1.</sup><br></p>
<p align='center'><img src='new_data/gif/1/mblur_Algebra.gif' width='30%'><img src='new_data/gif/1/usample-0.2_Algebra.gif' width='30%'><img src='' width='30%'><br><sup>De izquierda a derecha: difuminado mediano, sobremuestreo (ralentización) con probabilidad 0.2.</sup><br></p>
