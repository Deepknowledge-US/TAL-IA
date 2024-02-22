# CORPUS-SynLSE
This work is related to the upcoming paper _Synthetic Corpus Generation for Deep Learning Based Translation of Spanish Sign Language_. <!-- Añadir el vinculo al paper cuando esté -->

In this study, we propose two methods to synthetically create a gloss annotation corpus for LSE, and use them to explore the training hyperparameters of transformational models. In summary, the main contributions of this study are:
* To provide an overview of Deep Learning-based techniques approached to address the problem of communication between the deaf community and hearing people in the literature, as well as establish the main gaps in recent studies related to these tasks.
* The development of two methods to obtain synthetic gloss annotations in LSE: one based on the translation of an existing dataset (from German to Spanish), and another by a flexible rule-based system to translate from Oral Spanish (LOE, from _Lengua Oral Española_) to LSE glosses.
* The publication of a synthetic corpus including Spanish sentence pairs (LOE) and their corresponding translation to LSE glosses annotations.
* To carry out a set of experiments with language models based on Transformers using our synthetic datasets in both directions: the translation in LOE from LSE to written/oral language (gloss2text), and from written/oral language to glossed sentences (text2gloss).

Available code with the necessary scripts for the creation of the corpus and the corpus itself can be found in the following repository [LSEGloss2SpanishText](https://github.com/celiabotlop/LSEGloss2SpanishText.git)
