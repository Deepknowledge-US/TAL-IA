This work is related to the forthcoming paper Synthetic video for Sign Language Anonymization.

In this study, we propose two techniques for Sign Language video anonymization. In summary, the main contributions of this study are:

* **Addressing privacy concerns:** providing an overview about the privacy issue due to possible misuse of data, negatively impacting the collection of sign language datasets, which are indispensable for conducting sign language related studies.
* **Proposing two anonymization methods:** we introduce and compare two techniques for generating anonymized videos in Spanish Sign Language (LSE). The first method utilizes Face Swapping to obscure the signer’s identity while preserving the linguistic content, and the second employs avatar generation to fully synthesize anonymized signers.
* **Publication of anonymized dataset:** we will publicly release the anonymized LSE video.
* **Comprehensive evaluation framework:** our study combines quantitative and qualitative assessments to evaluate the effectiveness of anonymization techniques. We use technical metrics such as FID and LPIPS to measure the visual quality of the anonymized videos. In addition, we conduct a qualitative evaluation by collecting feedback from deaf volunteer participants through a survey, ensuring that the intelligibility and clarity of the signed content remains intact. This dual evaluation approach ensures a more holistic understanding of the trade-offs between privacy and content preservation.

* ## Table of contents
1. [Methodology](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#methodology)
  - [Video synthesis with avatars](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#avatars)
  - [Video synthesis with Face Swapping](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#Face-Swapping)
2. [Evaluation metrics](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#evaluation-metrics)
  - [Technical Metrics](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#Technical-Metrics)
  - [Subjective Evaluation](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#Subjective-Evaluation)
3. [Results](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#results)
4. [Future work](https://github.com/Deepknowledge-US/TAL-IA/tree/main/Synthetic_video_for_Sign_Language_Anonymization#future-work)

### Methodology
This section explains the tools used to generate the anonymized videos. 
Our approach begins with the selection of the set of videos on which the techniques to anonymize them would be applied, with the goal that the meaning and flow of the content remain intact. The dissemination of this dataset is restricted by the need to protect the identity of the participants, who have not given permission for the distribution of their image.

### Avatars
To anonymize the videos using avatars, we employed the [*Dollars Mocap tool*](https://www.dollarsmocap.com/), an AI-based motion capture solution that facilitates the creation of realistic animations. This tool offers several versions, such as Dollars MONO and EGAO, which allow real-time motion capture from cameras or video files, generating motion data that can then be used in game development engines such as Unity or Unreal Engine.

The animation process begins with MediaPipe, which captures and pre-processes the frames, extracting key points from the body, hands and face. Then, tracking algorithms ensure the consistency of these points between frames. Finally, the results are processed and exported to generate the avatar.

In our studio, we use Dollars MONO and EGAO to capture and animate the skeletal models, allowing us to edit the motion data in programs such as Maya, Blender or 3DS Max, which offers flexibility in the creation of animations.

<!-- Añadir la imagen del pipeline de mocap -->

### Face Swapping
Face swapping is a process that replaces one person's face in a video or image with another's, using computer vision and image processing techniques. The system detects facial landmarks in both faces, aligns the features, and blends them to ensure smooth integration, maintaining lighting, textures, and expressions.

A key challenge is ensuring visual realism and efficiency. For this, we used [*DeepFaceLive*](https://github.com/iperov/DeepFaceLive), built on the DeepFaceLab framework, which allows real-time face-swapping from webcam or video. The tool also enables training custom face models, so we could use our own datasets for new video synthesis.

The model training process involves two videos: a source video (with the face to swap in) and a target video (to replace the face). Frames are extracted from both videos, and faces are detected and processed. After cleaning unwanted data, a neural network is trained on these faces to merge them effectively in the new video.

DeepFaceLive offers customization options, enabling us to adjust the face-swapping process to meet the ethical and technical demands of sign language video anonymization.

### Evaluation Metrics
The anonymization quality will be assessed using both objective technical metrics and qualitative evaluations. A group of deaf individuals knowledgeable in LSE will participate in a survey to evaluate predefined aspects, determining whether the anonymization sufficiently preserves the message content. Additionally, computer vision techniques will be applied to objectively test the accuracy of Face Swapping in a technical context.

This combined approach will provide a comprehensive understanding of the effectiveness of the anonymization techniques, ensuring both visual quality and the practical preservation of Sign Language communication.

### Technical Metrics
LPIPS and FID are the metrics used to evaluate the quality of Face Swapping anonymization. Both metrics are established in literature for avatar generation and dataset anonymization.

- **LPIPS** assesses perceptual similarity by using deep neural networks like VGG or AlexNet to extract features that align with human visual perception. A higher LPIPS value indicates greater perceptual differences between images.

- **FID** evaluates the quality of images from generative models, comparing feature distributions of generated and real images using Inception v3. A lower FID suggests that generated videos accurately maintain key elements, such as hand movements and facial expressions, despite changes in the signer’s identity.

  
### Subjective evaluation
We designed a survey for deaf participants to evaluate three scientific questions regarding video anonymization techniques. The survey is structured into three parts, where participants watch videos of deaf individuals signing specific phrases.

- **Part 1 - Anonymization through Face Swapping:** This section assesses whether the face-swapping technique effectively anonymizes the signer’s identity. Participants answer two questions: one about recognizing the signer and another regarding the success of the anonymization.

- **Part 2 - Preservation of Meaning with Facial Expression:** This part examines whether face-swapping retains the meaning of signs that rely heavily on facial expressions. Participants view videos of ambiguous signs and select the correct meanings from provided options.

- **Part 3 - Comprehensibility of Avatars:** Here, avatars replace face-swapping. Participants respond to three questions about their understanding of sentences signed by avatars, comparing them to original videos and suggesting improvements for clarity.

To create effective survey questions, we collaborated with contacts from the Deaf community and interpreters. For Part 1, we identified notable individuals for Face Swapping, resulting in videos featuring five signers. Part 2 focused on signs whose meanings depend on facial expressions, utilizing videos from a single expressive signer. Part 3 included 14 sentences generated with two avatars. The survey used a random subset of videos for each respondent to minimize fatigue.
### Results

### Future work
