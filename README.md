# Progetto OpenWeather

**Table of Contents**
- [Introduzione](#id-section1)
- [API OpenWeatherMap.org](#id-section2)
- [Rotte](#id-section3)
- [TEST](#id-section4)
- [Utilizzo](#id-section5)
- [Autori e Sviluppo](#id-section6)

<div id='id-section1'/>

# Introduzione
Il progetto è un applicazione Spring che permette di ottenere informazioni e statistiche sulle temperature di una specificata località.
Le informazioni provengono dalle API di OpenWeatherMap.org (inserire link), sono disponibili tramite la nostra applicazione:
- Informazioni meteo sulla temperatura reale, percepita, minima e massima
- Informazioni attuali
- previsioni ogni 3 ore per i successivi 5 giorni

<div id='id-section2'/>

# API OpenWeatherMap.org
OpenWeatherMap.org mette a disposizione un numeroso set di API sia gratuite che a pagamento.
Il nostro progetto sfrutta esclusivamente API gratuite ottenibili da chiunque solo registrandosi presso il loro sito.
L'API-Key è necessaria per usufruire anche delle API gratuite, nel nostro codice è possibile midificare tale API-Key per qualsiasi scopo con un altra, in particolare:

`private String apiKey = "0***************************************f";`


<div id='id-section3'/>

# Rotte
## Panoramica
| Rotte | Descrizione                    |
| ------------- | ------------------------------ |
| `/getMetadata`      | Permette di ottenre i metadati in formato JSON       |
| `/getCurrentWeather`   | Permette di ottenere i dati meteo attuali di una certa località    |
| `/getWeatherForecast`   | Permette di ottenre le previsioni meteo di una certa località |

## Rotta */getMetadata*
Descrizione rotta
## Rotta */getCurrentWeather*
Descrizione rotta
## Rotta */getWeatherForecast*
Descrizione rotta

<div id='id-section4'/>

# TEST

<div id='id-section5'/>

# Utilizzo
Dopo aver avviato l'applicazione tutte le rotte implementate possono essere chiamate da qualsiasi browser o da un applicativo quale Postman nel segente modo:

`localhost:8080/<nome_rotta>`

Nota: 8080 è la porta standard, in caso di necessita puo essere modificata con una qualsisasi altra porta nel file: "nome-file-e-percorso"

<div id='id-section6'/>

# Autori e Sviluppo
Il presente progetto è stato sviluppato all'interno del corso di Programmazione ad Oggetti Anno Accademico 2021/2022 da:
- Federico Brunella
-Leonardo Pieralisi