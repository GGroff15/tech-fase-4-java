# Multimodal Medical Patient Screening System

## Overview

This project is a multimodal medical patient screening system designed to help classify the urgency of a patient's condition. It processes video and audio recordings from patient screenings, as well as manually submitted medical reports, to provide a comprehensive and real-time assessment of patient urgency.

The patient classification system is inspired by the Manchester Triage System (Manchester Protocol), using a color-coded urgency scale to prioritize care based on clinical risk and presenting symptoms.

The system will use NLP (Natural Language Processing) to extract important clinical information from the medical report. This extracted data will be used by the classification engine, which is based on Manchester Protocol features. NLP will act exclusively as an interpretation and normalization mechanism, ensuring that clinical information is standardized for accurate classification.

## Main Elements of Medical History
The system extracts and analyzes the following elements from the medical report:
- **Main complaint**: Description of the problem that led the patient to seek care.
- **Onset and progression**: When the symptoms started and how they have progressed.
- **Specific characteristics**: Intensity, location, triggering or relieving factors.
- **Previous medical history**: Chronic diseases, surgeries, allergies, and medication use.
- **Risk factors**: Smoking, alcohol use, family history, among others.

## Identification and Monitoring of Vital Signs
The following vital signs are assessed to support patient classification:
- **Body temperature**: High fever may indicate serious infection, while hypothermia is a sign of shock or exposure to cold.
- **Blood pressure**: Hypotension may indicate shock, while severe hypertension may be associated with emergencies such as stroke or heart attack.
- **Heart rate**: Tachycardia may be a sign of pain, fever, or shock; bradycardia may indicate serious heart problems.
- **Respiratory rate**: Changes suggest respiratory failure or metabolic acidosis.
- **Oxygen saturation**: Values below 90% indicate hypoxemia and the need for immediate support.


### Modalities Processed
- **Audio**: Extracts speech and analyzes emotions from the audio stream.
- **Video**: Detects patient facial emotions in real time. While patient screening, the system can also detect violence through video analysis.
- **Medical Report**: Extracts key points, reads biometric data, symptoms, and other observations from the report.

Each modality is processed individually and in real time. The results are then aggregated to classify the patient's condition. If necessary, the system will notify an emergency team.

### Classification Table
| Color  | Urgency Level      | Description                                                      |
|--------|--------------------|------------------------------------------------------------------|
| Red    | Emergency          | Extremely serious, requires immediate attention (triggers team)   |
| Yellow | Urgent             | Urgent, requires fast attention, but can wait                     |
| Green  | Not very urgent    | Low complication risk, can await attention                        |

- **Red** classification triggers the emergency team.

### Data Handling
- **Audio and Video**: Streamed and stored in real time.
- **Medical Report**: Stored upon receipt.

## Technical Details
- **Azure Blob Storage**: Persists uploaded audio, video, and medical reports.
- **PostgreSQL**: Stores metadata for audio, video, and reports, as well as processing results and patient condition categorizations.
- **YOLOv8**: External service for video analysis.
- **Azure Speech Service**: Converts speech to text ([Azure Speech Service for Java](https://learn.microsoft.com/en-us/azure/ai-services/speech-service/how-to-recognize-speech?pivots=programming-language-java)).
- **LLM Models**: Used to process medical reports and classify patient conditions based on all processed information.

## How to Run

### Prerequisites
- Java 17+
- Maven
- Docker & Docker Compose

### Setup Steps
1. **Clone the repository**
2. **Configure environment variables** as needed (see `application.yaml` for configuration options).
3. **Start dependencies** (PostgreSQL, Azure Blob Storage emulator, YOLOv8 service) using Docker Compose:
   ```sh
   docker-compose up -d
   ```
4. **Build the project**:
   ```sh
   ./mvnw clean install
   ```
5. **Run the application**:
   ```sh
   ./mvnw spring-boot:run
   ```

### Notes
- Ensure you have access to Azure services and external APIs as required.
- Update configuration files with your credentials and endpoints.
- For speech-to-text, follow the [Azure Speech Service Java guide](https://learn.microsoft.com/en-us/azure/ai-services/speech-service/how-to-recognize-speech?pivots=programming-language-java).

## Contributing
Contributions are welcome! Please open issues or submit pull requests for improvements or bug fixes.

## License
This project is licensed under the MIT License.
