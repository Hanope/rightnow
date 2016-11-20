var directionsService = new google.maps.DirectionsService;
var directionsDisplay = new google.maps.DirectionsRenderer;
var routeList = new Array();

var start;
var end;
var map;
var currentPos;

$('#btn-make-plan').click(function() {
    var startLocation = $('#start').val();
    var endLocation = $('#end').val();

    start = getLatLng(startLocation);
    end = getLatLng(endLocation);

    directionsDisplay.setMap(map);
    calculateAndDisplayRoute(directionsService, directionsDisplay);
});

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 16,
        center: {lat: 37.500439, lng: 126.867633}
    });

    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            currentPos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            map.setCenter(currentPos);
        });
    }
}

function calculateAndDisplayRoute(directionsService, directionsDisplay) {
    directionsService.route({
        origin: start,
        destination: end,
        travelMode: google.maps.TravelMode.TRANSIT,
        transitOptions: {
            departureTime: new Date(Date.now()),
            modes: [google.maps.TransitMode.BUS, google.maps.TransitMode.SUBWAY],
            routingPreference: google.maps.TransitRoutePreference.FEWER_TRANSFERS
        }
    }, function(response, status) {
        if (status === google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
            routeList.push(response);

            showSchedule(response);
        } else {
            window.alert('Directions request failed due to ' + status);
        }
    });
}

function getLatLng(location) {
    switch (location) {
        case 'current':
            return currentPos;
        case 'dongyang':
            return {lat: 37.500439, lng: 126.867633};
            break;
        case 'gocheckdom':
            return {lat: 37.499053, lng: 126.867940};
            break;
        case 'namsanTower':
            return {lat: 37.551242, lng: 126.988414};
            break;
        case 'gwanghwamun':
            return {lat: 37.575958, lng: 126.976998};
    }
}

function showSchedule(json) {
    var text;

    try {
        var departureTime = json['routes'][0]['legs'][0]['departure_time']['text'];
        var arrivalTime = json['routes'][0]['legs'][0]['arrival_time']['text'];
        var totalDistance = json['routes'][0]['legs'][0]['distance']['text'];
        var totalDuration = json['routes'][0]['legs'][0]['duration']['text'];
        var step = json['routes'][0]['legs'][0]['steps'];

        text = '출발시간 : ' + departureTime + '\n' +
            '도착시간 : ' + arrivalTime + '\n' +
            '총거리 : ' + totalDistance  + '\n' +
            '총시간 : ' + totalDuration  + '\n';

        for(i in step) {
            var travelMode = step[i]['travel_mode'];
            var distance = step[i]['distance']['text'];
            var duration = step[i]['duration']['text'];
            var instructions = step[i]['instructions'];

            var cnt = parseInt(i) + 1;


            text += '\n-----------' + cnt + '-----------\n' +
                '이동안내 : ' + instructions + '\n' +
                '이동거리 : ' + distance + '\n' +
                '이동시간 : ' + duration + '\n';

            if(travelMode != 'WALKING') {
                var departureLocation = step[i]['transit']['departure_stop']['name'];
                var departureTime = step[i]['transit']['departure_time']['text'];
                var arrivalLocation = step[i]['transit']['arrival_stop']['name'];
                var arrivalTime = step[i]['transit']['arrival_time']['text'];
                var lineNumber = step[i]['transit']['line']['short_name'];
                var vehichle = step[i]['transit']['line']['vehicle']['name'];
                var headsign = step[i]['transit']['headsign'];


                text += '교통수단 : ' + vehichle + ' ' + lineNumber + '\n' +
                    '이동방향 : ' + headsign + '\n' +
                '승차장소 : ' + departureLocation + '\n' +
                '출발시간 : ' + departureTime + '\n' +
                '하차장소 : ' + arrivalLocation + '\n' +
                '도착시간 : ' + arrivalTime + '\n\n';
            }

        }
    } catch(e) {
        var totalDistance = json['routes'][0]['legs'][0]['distance']['text'];
        var totalDuration = json['routes'][0]['legs'][0]['duration']['text'];

        text = '총거리 : ' + totalDistance  + '\n' +
                '총시간 : ' + totalDuration  + '\n';
    }


    $('#plan_result').html(text);
}


initMap();