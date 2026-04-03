# FaceBreak

An Android app for facial analysis using TensorFlow Lite models.

## Setup

Several files required to build this project are stored in a separate private repository for security reasons. Authorized contributors can obtain the ML models, `local.properties`, and `google-services.json` from there. To request access, contact the repository owner.

### ML Models

The TensorFlow Lite model files (`.tflite`) are **not** included in this repo.

Once you have the model files, place them in:

```
app/src/main/ml/
```

The following models are required:

- `AgeModel5.tflite`
- `AncestryModel9.tflite`
- `CharacterFlawsModel3.tflite`
- `CharacterModel4.tflite`
- `EmotionsModel1600.tflite`
- `EyeColorModel3.tflite`
- `EyebrowsModel.tflite`
- `FaceShapeModel1000d.tflite`
- `FeaturesFaceModel5.tflite`
- `GenderModel2.tflite`
- `HairColorModel8.tflite`
- `HairStyleModel4.tflite`
- `HeadwearModel3.tflite`
- `JawModel5.tflite`

### Local Properties

The `local.properties` file is not included in the repo. Android Studio will auto-generate it when you first open the project, setting `sdk.dir` to your local Android SDK path.

This file also contains a `base64EncodedPublicKey` used for Google Play billing verification. If you need in-app purchase functionality, obtain this key from the private repository. Add it to your `local.properties`:

```
base64EncodedPublicKey=<key obtained from repository owner>
```

### Firebase Configuration

The `app/google-services.json` file is not included in the repo. This file is required for Firebase services. You have two options:

1. **Use the existing Firebase project** -- Obtain the `google-services.json` file from the private repository and place it in the `app/` directory.

2. **Create your own Firebase project** -- Set up a new project in the [Firebase console](https://console.firebase.google.com/), register an Android app with the package name `com.thomasjbarrerasconsulting.faces`, and download the generated `google-services.json` into the `app/` directory.
