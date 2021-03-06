# Progetto OpenWeather

- [Introduzione](#id-section1)
- [API OpenWeatherMap.org](#id-section2)
- [Utilizzo](#id-section3)
- [Rotte](#id-section4)
- [TEST](#id-section5)
- [Autori e Sviluppo](#id-section6)

<div id='id-section1'/>

# Introduzione
Il progetto è un applicazione Spring che permette di ottenere informazioni e statistiche sulle temperature di una specificata località.
Sono disponibili tramite la nostra applicazione:
- Informazioni sulle condizioni meteo attuali.
- Previsioni ogni 3 ore per i successivi 5 giorni.
- Statistiche sulle previsioni meteo relative a: temperatura reale, percepita, massima e minima.

Le informazioni provengono dalle API di [OpenWeatherMap.org](https://openweathermap.org/).


<div id='id-section2'/>

# API OpenWeatherMap.org
[OpenWeatherMap.org](https://openweathermap.org/api) mette a disposizione un numeroso set di API sia gratuite che a pagamento.
Il nostro progetto sfrutta esclusivamente API gratuite, in particolare:
- [Current](https://openweathermap.org/current) Weather Data: permette di ottenere le informazioni meteo correnti di una qualsiasi località.
- 5 day/3 hour [Forecast](https://openweathermap.org/forecast5): permette di ottenere previsioni meteo di una qualsiasi località per i prossimi 5 giorni ad intervalli di 3 ore.


Per usufruire delle API gratuite è necessario ottenere un API-key, anch'essa gratuita, registrandosi su OpenWeatherMap.org.
Un'API-key è già inserita all'interno del codice per garantirne il funzionamento, per ogni necessità può essere modificata nel file [WeatherServiceimpl.java](https://github.com/federicobrunella/ProgettoCorso/blob/main/OpenWeather/src/main/java/it/univpm/OpenWeather/service/WeatherServiceImpl.java):

```java
 private String apiKey = "0***************************************f";
```


<div id='id-section3'/>

# Utilizzo
Dopo aver avviato l'applicazione, tutte le rotte implementate possono essere chiamate da un browser qualsiasi o da un applicativo quale Postman.

### Esempio

`localhost:8080/<nome_rotta>`

Nota: 8080 è la porta standard, in caso di necessità puo essere modificata con una qualsiasi altra porta nel file di configurazione: [application.properties](https://github.com/federicobrunella/ProgettoCorso/blob/main/OpenWeather/src/main/resources/application.properties)


<div id='id-section4'/>

# Rotte
## Panoramica
| Rotte | Tipo |Descrizione                    |
| ------------- | ---- | ------------------------------ |
| `/getMetadata`      | GET | Permette di ottenere i metadati in formato JSON       |
| `/getCurrentWeather`   | GET | Permette di ottenere i dati meteo attuali di una certa località    |
| `/getWeatherForecast`   | GET | Permette di ottenere le previsioni meteo di una certa località |
| `/getDailytStats`   | GET | Permette di ottenere le statistiche sulle previsioni meteo di una certa località con filtraggio su base giornaliera|
| `/getTimeSlotStats`   | GET | Permette di ottenere le statistiche sulle previsioni meteo di una certa località con filtraggio per fascia oraria e su base giornaliera|

## Rotta */getMetadata*
Questa rotta permette di visualizzare i metadati, ovvero una descrizione di tutti gli attributi e dei reltivi tipi di dato.
Questo output viene mostrato in formato JSON.

### Esempio
Rotta: `localhost:8080/getMetadata`

La risposta a questa richiesta sarà un JSON contenente i metadati:

```json
{
    "country": " : country [type: String]",
    "city": "city name [type: String]",
    "weatherData": [
        {
            "dt": " : UNIX timestamp [type: int]",
            "temp": ": temperature [type: double]",
            "temp_min": ": minimum temperature [type: double]",
            "description": " : detailed descripion of the weather [type: String]",
            "main": " : descripion of the weather(ex. sunny) [type: String]",
            "temp_max": ": maximum temperature [type: double]",
            "feels_like": ": feels like temperature [type: double]",
            "txtDateTime": " : human readble timestamp (format: YYYY-MM-DD hh:mm:ss)[type: String]"
        }
    ],
    "ID": " : city ID [type: String]"
}
```

## Rotta */getCurrentWeather*
Con questa rotta si possono visualizzare, in formato JSON, le informazioni relative alle condizioni meteo attuali di una località specificata nella richiesta.
Questa richiesta accetta il parametro 'city' per specificare la località su cui effettuare la richiesta.

Riassumendo i parametri accettati sono:
| Parametro  | Descrizione |
| ------------- | ------------- |
| `city`  | località per la quale ottenere i dati meteo  |

### Esempio
Rotta: `localhost:8080/getCurrentWeather?city=Jesi`

La risposta a questa richiesta sarà un JSON contenente i dati meteo attuali per la città di Jesi(AN):

```json
{
    "country": "IT",
    "city": "Jesi",
    "weather": [
        {
            "dt": -835086110,
            "temp": 281.59,
            "temp_min": 279.82,
            "description": "clear sky",
            "main": "Clear",
            "temp_max": 282.64,
            "feels_like": 280.0,
            "txtDateTime": "2021-12-18 16:47:00"
        }
    ],
    "ID": "6541474"
}
```

## Rotta */getWeatherForecast*
Con questa rotta si possono ottenere in formato JSON le previsioni meteo per la città specificata nella richiesta.
Questa richiesta accetta il parametro 'city' per specificare la località su cui effettuare la richiesta.

Riassumendo i parametri accettati sono:
| Parametro  | Descrizione |
| ------------- | ------------- |
| `city`  | località per la quale ottenere i dati meteo  |

### Esempio
Rotta: `localhost:8080/getWeatherForecast?city=Jesi`

La risposta a questa richiesta sarà un JSON contenente le previsioni ogni 3 ore per i successivi 5 giorni relative la città di Jesi(AN):

```json
{
    "country": "IT",
    "city": "Jesi",
    "weather": [
        {
            "dt": 1639850400,
            "temp": 280.49,
            "temp_min": 279.55,
            "description": "clear sky",
            "main": "Clear",
            "temp_max": 280.49,
            "feels_like": 280.49,
            "txtDateTime": "2021-12-18 18:00:00"
        },

        ...

        {
            "dt": 1640271600,
            "temp": 282.53,
            "temp_min": 282.53,
            "description": "overcast clouds",
            "main": "Clouds",
            "temp_max": 282.53,
            "feels_like": 281.71,
            "txtDateTime": "2021-12-23 15:00:00"
        }
    ],
    "ID": "6541474"
}
```
Nota: per migliorare la leggibilità del documento nell'esempio di risposta sopra, il JSON è stato tagliato e riporta solo la prima previsione disponibile e l'ultima.

## Rotta */getDailytStats*
Con questa rotta si possono ottenere le statistiche relative ai valori di temperatura reale e temperatura percepita.
In particolare con questa richiesta posso filtrare i dati su cui effettuare le statistiche su base giornaliera, nello specifico questa rotta, oltre al parametro `city`, accetta il parametro `days` che indica l'intervallo temporale su cui le statistiche verrano calcolate.
Ad esempio se nella richiesta è specificato il parametro `days=2` i valori medi verranno calcolati per i successivi 2 giorni dal momento in cui viene effettuata la richiesta.

Riassumendo i parametri accettati sono:
| Parametro  | Descrizione |
| ------------- | ------------- |
| `city`  | località per la quale ottenere i dati meteo  |
| `days`  | filtro su base giornaliera (accettati valori da 1 a 5)  |

### Esempio
Rotta: `localhost:8080/getDailytStats?city=Jesi&days=2`

La risposta a questa richiesta sarà un JSON contenente le statistiche relative la città di Jesi(AN) calcolate per i successivi 2 giorni dal momento della richiesta:

```json
{
    "country": "IT",
    "stats": {
        "avg_TempMax": 281.4981250000001,
        "avg_FellsLike": 280.22249999999997,
        "avg_TempMin": 281.4425,
        "absolute_TempMax": 283.82,
        "absolute_tempMin": 278.97,
        "avg_Temp": 281.44562500000006
    },
    "city": "Jesi",
    "id": "6541474"
}
```

## Rotta */getTimeSlotStats*
Con questa rotta si possono ottenere le statistiche relative ai valori di temperatura reale e temperatura percepita.
In particolare con questa richiesta posso filtrare su base giornaliera e su fascia oraria, la rotta accetta quindi i parametri:
| Parametro  | Descrizione |
| ------------- | ------------- |
| `city`  | località per la quale ottenere i dati meteo  |
| `days`  | filtro su base giornaliera (accettati valori da 1 a 5)  |
| `timeSlot`  | filtro per fascia oraria (accettati parametri 00, 03, 06, ..., 21) |

### Esempio
Rotta: `localhost:8081/getTimeSlotStats?city=Jesi&days=4&timeSlot=6`

La risposta a questa richiesta sarà un JSON contenente le statistiche meteo calcolate per 4 giorni dal momento della richiesta sulla fascia oraria delle 06:00 (le sei del mattino), il tutto relativo alla città di Jesi(AN).

```json
{
    "country": "IT",
    "stats": {
        "avg_TempMax": 280.235,
        "avg_FellsLike": 279.4225,
        "avg_TempMin": 280.235,
        "absolute_TempMax": 282.09,
        "absolute_tempMin": 279.17,
        "avg_Temp": 280.235
    },
    "city": "Jesi",
    "id": "6541474"
}

```

Nota: il parametro `timeSlot` indica quindi l'intervallo orario al quale l'utente è interessato, osservato che l'API di OpenWeatherMap.org restituisce le previsioni ogni 3 ore sempre a partire dalle 00:00, otteniamo che le fascie orarie disponibili sono quelle che vanno dalle 00 alle 21 (00, 03, 06, ..., 21)

<div id='id-section5'/>

# TEST
Sono stati implementati 3 Unit Test sviluppati in JUnit5:
- [StatisticsTest](https://github.com/federicobrunella/ProgettoCorso/blob/main/OpenWeather/src/test/java/it/univpm/OpenWeather/StatisticsTest.java): test per verificare il corretto funzionamento della classe relativa al calcolo delle statistiche.
- [WrongDaysValueException](https://github.com/federicobrunella/ProgettoCorso/blob/main/OpenWeather/src/test/java/it/univpm/OpenWeather/WrongDaysValueExceptionTest.java): test per verificare il funzionamento delle eccezioni relative al filtro su base giornaliera.
- [WrongTimeSlotValueException](https://github.com/federicobrunella/ProgettoCorso/blob/main/OpenWeather/src/test/java/it/univpm/OpenWeather/WrongTimeSlotValueExceptionTest.java): test per verificare il funzionamento delle eccezioni relative al filtro su fascia oraria.

<div id='id-section6'/>

# Autori e Sviluppo
Il presente progetto è stato sviluppato all'interno del corso di Programmazione ad Oggetti (2021/2022) da:
- [Federico Brunella](https://github.com/federicobrunella)
- [Leonardo Pieralisi](https://github.com/LeonardoPieralisi)