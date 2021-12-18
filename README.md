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
Le informazioni provengono dalle API di OpenWeatherMap.org (inserire link), sono disponibili tramite la nostra applicazione:
- Informazioni sulle condizioni meteo attuali.
- previsioni ogni 3 ore per i successivi 5 giorni.
- Statistiche sulle previsioni meteo relative a: temperatura reale, percepita, massima e minima.


<div id='id-section2'/>

# API OpenWeatherMap.org
OpenWeatherMap.org mette a disposizione un numeroso set di API sia gratuite che a pagamento.
Il nostro progetto sfrutta esclusivamente API gratuite, in particolare:
- Current Weather Data: permette di ottenere le informazioni meteo correnti di una qualsisasi località.
- 5 day/3 hour Forecast: permette di ottenere previsioni meteo di una qualsiasi localittà per i prossimi 5 giorni ad intervalli di 3 ore.
Per usufruire delle API gratuite è necessario ottenere un API-key, anch'essa gratutita, registrandosi su OpenWeatherMap.og.
Un'API-key è già inserita all'interno del codice per garantirne il funzionamento, per ogni necessità può essere modificata nel file WeatherServiceimpl.java:

`private String apiKey = "0***************************************f";`

(TODO: aggiungere file di configurazione)


<div id='id-section3'/>

# Utilizzo
Dopo aver avviato l'applicazione tutte le rotte implementate possono essere chiamate da qualsiasi browser o da un applicativo quale Postman nel segente modo:

`localhost:8080/<nome_rotta>`

Nota: 8080 è la porta standard, in caso di necessità puo essere modificata con una qualsisasi altra porta nel file di configurazione: "nome-file-e-percorso + link"


<div id='id-section4'/>

# Rotte
## Panoramica
| Rotte | Tipo |Descrizione                    |
| ------------- | ---- | ------------------------------ |
| `/getMetadata`      | GET | Permette di ottenre i metadati in formato JSON       |
| `/getCurrentWeather`   | GET | Permette di ottenere i dati meteo attuali di una certa località    |
| `/getWeatherForecast`   | GET | Permette di ottenere le previsioni meteo di una certa località |
| `/getDailytStats`   | GET | Permette di ottenere le statistiche sulle previsioni meteo di una certa località con filtraggio su base giornaliera|
| `/getTimeSlotStats`   | GET | Permette di ottenere le statistiche sulle previsioni meteo di una certa località con filtraggio per fascia oraria e su base giornaliera|

## Rotta */getMetadata*
Questa rotta permette di visualizzare i metadati in formato JSON.
Ovvero una descrizione di tutti gli attributi che restituisce e dei reltivi tipi di dato.


## Rotta */getCurrentWeather*
Con questa rotta si possono visualizare in formato JSON le informazioni relative alle condizioni meteo attuali di una località specificata nella richiesta.
Qesta richiesta accetta il parametro 'city' per specificare la località su cui effettuare la richiesta.

Riassumendo i parametri accettati sono:
| Parametro  | Descrizione |
| ------------- | ------------- |
| `city`  | località per la quale ottenere i dati meteo  |

### Esempio
Rotta: `localhost:8081/getCurrentWeather?city=Jesi`

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
Con questa rotta posso ottenere in formato JSON le previsioni meteo per la città specificata nella richiesta.
Qesta richiesta accetta il parametro 'city' per specificare la località su cui effettuare la richiesta.

Riassumendo i parametri accettati sono:
| Parametro  | Descrizione |
| ------------- | ------------- |
| `city`  | località per la quale ottenere i dati meteo  |

### Esempio
Rotta: `localhost:8081/getWeatherForecast?city=Jesi`

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
Nota: per migliorare la leggibilità del documento nell'esempio di risposta sopra, il JSON è stato tagliato e riporta solo la prima previsione disponibile e l'ultima, normalmente il json che si ottiene come risposta conterrebbe nel JSONArray contenente le previsioni 40 JSON.

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
Rotta: `localhost:8081/getDailytStats?city=Jesi&days=2`

La risposta a questa richiesta sarà un JSON contenente le statistiche relative la città di Jesi(AN) calcolate per i successivi 2 giorni dal momento della richiesta:

```json
{
    "country": "IT",
    "stats": {
        "avg_TempMax": 280.10062500000004,
        "avg_FellsLike": 279.16437499999995,
        "avg_TempMin": 280.01187500000003,
        "avg_Temp": 280.10062500000004
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
        "avg_TempMax": 279.00749999999994,
        "avg_FellsLike": 277.93,
        "avg_TempMin": 279.00749999999994,
        "avg_Temp": 279.00749999999994
    },
    "city": "Jesi",
    "id": "6541474"
}
```

Nota: il parametro `timeSlot` indica quindi l'intervallo orario al quale l'utente è interessato, osservato che l'API di OpenWeatherMap.org restituisce le previsioni ogni 3 ore sempre a partire dalle 00:00, otteniamo che le fascie orarie disponibili sono quelle che vanno dalle 00 alle 21 (00, 03, 06, ..., 21)

<div id='id-section5'/>

# TEST

<div id='id-section6'/>

# Autori e Sviluppo
Il presente progetto è stato sviluppato all'interno del corso di Programmazione ad Oggetti (2021/2022) da:
- Federico Brunella
- Leonardo Pieralisi