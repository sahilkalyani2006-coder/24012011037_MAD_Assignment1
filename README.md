# Campus Companion – Ganpat University Route Finder

**Project name:** `24012011037_MAD_Assignment1`

Campus Companion is a simple Kotlin Android application that helps students and visitors find walking directions between places inside Ganpat University, Kherva.

The user selects a starting point and destination. The application then opens the walking route in Google Maps or a web browser.

## Features

- Separate selectors for the starting point and destination.
- Several Ganpat University locations to choose from.
- **Swap places** option.
- Opens walking directions in Google Maps.
- Button to open the Ganpat University campus in Google Maps.
- Simple interface with clear instructions.
- Checks that the starting point and destination are different.

Some available places include:

- UVPCE New Building
- MBA Building
- Nescafe
- UVPCE Main Building
- University Office
- Central Library
- Vikram Neil Sports Academy
- Cafeteria
- Shopping Center
- Post Office
- State Bank of India
- Mehsana Urban Co-operative Bank
- B. S. Patel Polytechnic
- Acharya Motibhai Patel Institute of Computer Studies

## How to Use

1. Open the application.
2. Tap **Starting point** and choose your current location.
3. Tap **Destination** and choose where you want to go.
4. Use **Swap places** if the two selections need to be reversed.
5. Tap **Show walking route in Google Maps**.
6. Check both map pins before following the route.

Internet access and Google Maps, or a compatible browser, are required to view directions.

## Screenshots

<table>
  <tr>
    <td align="center"><strong>Main screen</strong><br><img src="screenshots/main_screen.png" width="330" alt="Campus Route Finder main screen"></td>
    <td align="center"><strong>Starting-point options</strong><br><img src="screenshots/start_options.png" width="330" alt="Starting-point selection list"></td>
  </tr>
  <tr>
    <td align="center"><strong>Destination options</strong><br><img src="screenshots/destination_options.png" width="330" alt="Destination selection list"></td>
    <td align="center"><strong>Walking route in Google Maps</strong><br><img src="screenshots/gmaps.png" width="330" alt="Walking directions opened in Google Maps"></td>
  </tr>
</table>

## Basic Application Flow

```text
Open the application
        ↓
Choose a starting point
        ↓
Choose a destination
        ↓
Tap the walking-route button
        ↓
View directions in Google Maps
```

## Technologies Used

| Technology | Purpose |
|---|---|
| Kotlin | Application logic |
| XML | Screen layout |
| Android Studio | Development environment |
| Spinner | Starting-point and destination selection |
| Button | User actions |
| Intent and URI | Opening Google Maps or a browser |


## How It Works

The application stores a list of campus locations. Each location has a name that is shown in the selection list and a value that can be sent to Google Maps.

When the route button is tapped, the app creates a walking-directions link using the selected starting point and destination. An Android intent opens that link in Google Maps. If the Google Maps app is unavailable, the link can open in a browser.

## Challenges Faced

- Choosing clear names for campus locations.
- Making the starting-point and destination lists easy to use.
- Passing both selected places correctly to Google Maps.
- Preventing the same place from being used as both points.
- Keeping the layout readable on a phone screen.
- Some map pins may need on-campus checking for exact placement.

## Limitations

- This version uses Google Maps or a browser for the final route.
- Internet access is required for directions.
- The walking route depends on the location data available in Google Maps.
- The app does not yet draw routes on its own built-in campus map.
- Campus place coordinates should be checked before real-world use.

## Author

**Enrollment number:** 24012011037  
**Course:** Mobile Application Development  
**Project:** Assignment 1
