# TAL.IA: TrAducción de la Lengua de signos mediante Inteligencia Artificial
<p align="center">
<img src="img/profile3.png" alt="Profile picture" width="60%">
</p>
 
  
  Sign Languages (SL) are the main medium of communication for the deaf community. However, only the deaf themselves, their families and interpreters (less than 1% of the total population) learn it. Consequently, improving communication with hearing people is a fundamental need for the deaf community. In this sense, this project proposes a system that, through the use of Artificial Intelligence (AI) techniques applied to Spanish Sign Language by means of computer vision, allows to convert a message in SL to its voice/text translation. 
  


## Table of contents
1. [Methodology](https://github.com/Deepknowledge-US/TAL-IA#methodology)
    - [CALSE-100 Dataset](https://github.com/Deepknowledge-US/TAL-IA#calse-100-dataset)
    - [Data augmentation](https://github.com/Deepknowledge-US/TAL-IA#data-augmentation)
    - [Deepfakes](https://github.com/Deepknowledge-US/TAL-IA#deepfakes)

2. [Implementation details](https://github.com/Deepknowledge-US/TAL-IA#implementation-details)

3. [Results](https://github.com/Deepknowledge-US/TAL-IA#results)

4. [Future work](https://github.com/Deepknowledge-US/TAL-IA#future-work)
     - [Other works](https://github.com/Deepknowledge-US/TAL-IA#other-works)

## Methodology

The first aim is to gather a sufficiently extensive dataset for training models focused on Isolated Sign Language Recognition (ISLR). For this purpose, a dataset called CALSE-100 (Conjunto Aislado de Lengua de Signos Española) composed of 100 gloss with 600 videos in total collected from different data sources has been defined and is available at [this address](https://uses0-my.sharepoint.com/:f:/g/personal/mptrigo_us_es/EnaUwfyXahFPoSOg_mj4JwEBsRXjKIVNDtMYpAqv714bhg?e=Q7tJ7L).   

In addition, data expansion techniques have been applied: [*data augmentation*](https://github.com/Deepknowledge-US/TAL-IA/tree/main/data_augmentation), which is usually used in several fields of deep learning to improve the quality and size of the training set, as well as [*Deepfakes*](https://github.com/Deepknowledge-US/TAL-IA/tree/main/deepfakes), which besides expanding the content of the training set will allow to anonymize the data while maintaining the facial expression of the signs.

### CALSE-100 Dataset 
The CALSE-100 set has been formed by obtaining videos from 3 different, publicly available data sources, which are the Dictionary of Spanish Sign Language ([DILSE](https://fundacioncnse-dilse.org/)) and Spread the Sign ([STS](http://www.spreadthesign.com/es.es/search/)) dictionaries, as well as the dataset from the University Community Assistance Service ([SACU](https://sacu.us.es/ne-prestaciones-discapacidad-glosario)) of the University of Seville.

<p align="center">
<img src="img/public_datasets.png" alt="Public datasets" width="70%">
</p>

CALSE-100 is composed of 100 glosses, with three video samples of each gloss extracted from the sources described above.

In addition, this set has also been signed by a professional Spanish Sign Language interpreter, thus being able to add more examples for each word in the set. Thanks to this collaboration, 3 more items of each word have been incorporated, thus obtaining a total of 6 examples of each word. 

To ensure that the sign language recognition model can handle the diverse scenarios that can arise in everyday life, it is essential to include variations in the signs' appearance and singing paces during training. To achieve this, we have defined several scenarios for the repetitions of videos signed by the interpreter. These scenarios include changes in perspective (such as front view or profile), the emphasis during sign execution, and variations in clothing, among other factors. 
This approach helps to introduce variability between different elements of the same signed words, ultimately leading to an enhanced dataset and a more robust model.

<p align="center">
<img src="img/macarena_items.png" alt="Macarena views" width="70%">
</p>



### Data augmentation
To increase datasets size that will launch augmentations on the videos, a new, publicly available [library](https://github.com/RodGal-2020/video_augmentation) has been used.

Although several functions are implemented to apply transformations, we will only consider affine transformations, since it includes the classical transformations, i.e. translations, reflections, scalings and rotations. In addition, the function applies a random affine transformation, which may therefore result in the application of one of the above transformations. However, the library adds the possibility to apply these transformations individually, to provide a complete tool.

<p align="center">
<img src="gif/000503.gif" alt="Profile picture" width="36%" style="margin-right: 10px">
<img src="gif/000510.gif" alt="Profile picture" width="25%" style="margin-right: 10px">
<img src="gif/000556.gif" alt="Profile picture" width="27%">
</p>



### Deepfakes
To avoid possible problems in maintaining identity through anonymization of the data, the [FaceSwap](https://github.com/deepfakes/faceswap) tool has been used, which employs Deep Learning techniques to recognize and swap faces in each of the signers that make up the CALSE-100 dataset

The face swapping to generate Deepfakes is composed of 3 stages: 
- Extraction: in this first stage, the extraction of faces from the target video for the later training takes place, in which face landmarks are recognized and the images are cropped, saving the faces to be used for training. In this first step, it is important to have a large set of images containing the face of the subject to be trained, as well as to consider the data's quality and the variety of angles and expressions.
- Training: in this step, the training of the `Phaze-a' model from FaceSwap is executed for 35,000-40,000 iterations (it depends on the models used for the face swapping), with a batch size of 10.
- Conversion: in this last stage, the face extraction is performed again (in this case, on the source video) and then the face swapping is performed to obtain the final set of videos with Deepfakes applied.

<p align="center">
<img src="img/DF.png" alt="Macarena views" width="70%">
</p>


## Implementation details
For the isolated recognition we have used the I3D network architecture used in [WLASL](https://github.com/dxli94/WLASL) implemented in PyTorch. 

All experiments utilized identical configurations, employing the Adam optimizer, a batch size of 10, a learning rate of 10<sup>-3</sup> and a total of 60 epochs. To assess the performance of the models, we calculate the average scores for top-K classification accuracy with K = {1, 5, 10}. This evaluation is conducted across all sign instances.

Due to the limited number of samples available for each sign, the dataset was divided into training and test sets only, applying the cross-dataset approach, so that one of the datasets is separated as a test set (equivalent to one example per sign) and the rest as a training set. Therefore, the experimental series was repeated three times: once with the SACU as test set, once with STS, and finally leaving DILSE out of the training set.

<p align="center">
<img src="img/preliminar_experiments.png" alt="preliminar experimentss" width="70%">
</p>

To assess the effect of data augmentation on CALSE-100 training, a test battery was created that merged original data from the training set with data generated through Deepfakes and other augmentations. For Deepfakes, three models (two male and one female) were utilized, with 200 new videos generated for each model. Not all of the Deepfake videos created during training were used.
Additionally, affine transformations were randomly applied to each video in the dataset, with AF1 and AF2 differing in the dataset to which they were applied. Specifically, AF1 corresponds to the affine transformation applied to our interpreter videos, while AF2 applies to videos from the public datasets. 

## Results 
Our proposal showed that augmentations during training generally improved the accuracy in the top-1, top-5, and top-10 metrics compared to the baseline experiment. The improvements ranged up to 32.59% in the top-1 metric. 

<figure>
  <p align="center">
  <img src="img/results_STS.png" alt="results STS">
  </p>
</figure>
<p align="center"><em>Experiment results executed using STS as a test set.</em></p>


<figure>
  <p align="center">
  <img src="img/results_DILSE.png" alt="results DILSE">
  </p>
</figure>
<p align="center"><em>Experiment results executed using DILSE as a test set.</em></p>


<figure>
  <p align="center">
  <img src="img/results_SACU.png" alt="results SACU">
  </p>
</figure>
<p align="center"><em>Experiment results executed using SACU as a test set.</em></p>


## Future work
In future work, we aim to increase the CALSE dataset up to 1000 glosses. Our focus will be on exploring alternative architectures for Sign Language Recognition, that will allow us to improve the accuracy of the results.

### Other works
In a first instance, a mobile application named [SignUS](https://github.com/Deepknowledge-US/TAL-IA/tree/main/signus) was developed to collect videos of deaf people, signers and interpreters in order to get a semi-labeled set of videos (the user records a predetermined phrase, but without segmenting each gloss). Although approximately 20 associations of deaf people are contacted, we do not get users that allow us to accumulate a new dataset. 

However, the application is easily adaptable to recording the deaf person and supplying the video to a translation model that returns the text to the deaf person.

In addition, we are carrying out 2 master's degree final projects associated with our working group related to this study, more specifically:
- [Sign2Text: Automatic Sign Language Translation.](https://github.com/manromero/transformer-slt)
- [Generation of automatic translations between LSE glosses and Spanish.](https://github.com/celiabotlop/LSEGloss2SpanishText) This project includes a new syntetic dataset of Spanish and LSE glosses generated by a rule-based system.

## Acknowledgments

The project was supported by [FEDER/Junta de Andalucía - Paidi 2020/ _Proyecto P20_01213](https://investigacion.us.es/sisius/sis_proyecto.php?ct=1&cs=&idproy=33683), which consists of the following members:
- [Juan Antonio Álvarez García](https://www.linkedin.com/in/juanantonioalvarezgarcia/) (PI)
- [Miguel Ángel Martínez del Amor](www.cs.us.es/~mdelamor) (research team)
- [Fernando Sancho Caparrini](www.cs.us.es/~fsancho) (research team)
- [Luis Miguel Soria Morillo](https://es.linkedin.com/in/lsoriamo) (research team)
- [Álvaro Arcos García](https://es.linkedin.com/in/alvaroarcos) (collaborator team) 
- [Diego Cabrera Mendieta](https://scholar.google.es/citations?user=ZIKw_DAAAAAJ&hl=es) (collaborator team)
- [Javier de la Rosa Pérez](https://www.linkedin.com/in/versae) (collaborator team)
- [Marina Perea Trigo](https://es.linkedin.com/in/marina-perea-trigo-0a2995189) (collaborator team)
- [Macarena Vilches Suárez](https://es.linkedin.com/in/macarenavilchess) (collaborator team)
- [José Luis Salazar González](https://es.linkedin.com/in/jose-luis-salazar-gonzalez-644136143) (staff)
- [José Antonio Rodríguez Gallego](https://es.linkedin.com/in/jos%C3%A9-antonio-rodriguez-gallego-bb2967176) (staff)
- [José Morera Figueroa](https://es.linkedin.com/in/jos%C3%A9-morera-figueroa-2344b0210) (staff)

The research associated with this project was also supported by grants from NVIDIA and used an A100 GPU donated by the NVIDIA HW Grant awarded to our colleague Miguel A. Martinez-del-Amor. 

We would like to especially thank the following team members for their work:
- Macarena Vilches Suárez
- José Antonio Rodríguez Gallego
- José Morera Figueroa
- José Luis Salazar González
