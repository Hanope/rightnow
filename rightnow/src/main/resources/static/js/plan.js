// createDistance 문제 발생
// cnt값이 반복되는 현상 발생
// route값 생성 문제


var totalMoney = 0;
var position;
var route = new Array();
var currentTime;
var nextAddress = 0;
var scheduleData;

$('#btn-plan-search').click(function () {
    var location = $("input[name='location']:checked").val();
    var money = $('#money').val();
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    var theme = $("input[name='theme']:checked").val();

    if (startDate == '' || endDate == '') {
        alert('날짜를 입력하세요.');
        return;
    }

    if (money == '') {
        alert('비용을 입력하세요.');
        $('#money').focus();
        return;
    }

    var eDate = new Date(endDate);
    var sDate = new Date(startDate);
    var betweenDay = parseInt((eDate.getTime() - sDate.getTime()) / 1000 / 3600 / 24) + 2;

    if(betweenDay >= 5) {
        alert('현재 개발 단계로\n5일 이상의 일정 서비스를 지원하지 않습니다.');
        return;
    }

    $.ajax({
        url: "./schedule/make",
        type: "GET",
        data: {
            'location': location,
            'money': money,
            'startDate': startDate,
            'endDate': endDate,
            'theme': theme
        },
        success: function (json) {
            totalMoney = 0;
            route = [];
            scheduleData = json;
            var t = JSON.stringify(json, null, "\t");

            $('#plan_result').text("");
            $('#plan_result').text(t);

            currentTime = new Date($('#startDate').val());
            currentTime.setHours(currentTime.getHours() - 9);

            saveData(json);

        }, error: function() {
            scheduleData = null;
        }
    });
});

function saveData(day) {
    position = [{lat: 37.500439, lng: 126.867633}];

    for(var i in day) {
        if(day[i]['breakfast'] != null)
            position.push({lat: day[i]['breakfast']['latitude'], lng: day[i]['breakfast']['longitude']})
        if(day[i]['event'] != null)
            position.push({lat: day[i]['event']['latitude'], lng: day[i]['event']['longitude']})
        if(day[i]['lunch'] != null)
            position.push({lat: day[i]['lunch']['latitude'], lng: day[i]['lunch']['longitude']})
        if(day[i]['tour'] != null)
            position.push({lat: day[i]['tour']['latitude'], lng: day[i]['tour']['longitude']})
        if(day[i]['dinner'] != null)
            position.push({lat: day[i]['dinner']['latitude'], lng: day[i]['dinner']['longitude']})
        if(day[i]['accommodation'] != null)
            position.push({lat: day[i]['accommodation']['latitude'], lng: day[i]['accommodation']['longitude']})
    }

    nextAddress = 0;
    calcRoute();

}

function makePlan(day) {
    $('.day_schedule').html('');

    if (day[0]['accommodation'] == null && day[0]['dinner'] == null) {
        day_schedule_container_null();
        return;
    }

    var num = 0;

    for (var i in day) {
        var cnt = 1;

        createDay(parseInt(i) + 1);

        if(day[i]['breakfast'] != null) {
            createSchedule(day[i]['breakfast'], cnt++, createDistance(num++));
        }
        if(day[i]['event'] != null) {
            createSchedule(day[i]['event'], cnt++, createDistance(num++));
        }
        if(day[i]['lunch'] != null) {
            createSchedule(day[i]['lunch'], cnt++, createDistance(num++));
        }
        if(day[i]['tour'] != null) {
            createSchedule(day[i]['tour'], cnt++, createDistance(num++));
        }
        if(day[i]['dinner'] != null) {
            createSchedule(day[i]['dinner'], cnt++, createDistance(num++));
        }
        if(day[i]['accommodation'] != null) {
            createSchedule(day[i]['accommodation'], cnt++,  createDistance(num++));
        }
    }

    createTotalCost();
}

function day_schedule_container_null() {
    var text = '<div class="day_schedule_container">' +
                    '<div class="day_schedule_no">조건에 맞는 일정이 없습니다.</div>' +
                '</div>'

    $('.day_schedule').html(text).hide().fadeIn(1000);
}

function createSchedule(schedule, cnt, duration) {
    var name = schedule['name'];
    var imgSrc = (schedule['image'] == '') ? '/img/default.png' : schedule['image'];
    var rating = schedule['rating'];
    var star = getStar(rating);
    var price = schedule['price'];
    var latitude = schedule['latitude'];
    var longitude = schedule['longitude'];
    var cTime = new Date(currentTime.getTime() + duration);
    var nTime;

    switch(schedule['typecode']) {
        case 1:
            nTime = new Date(cTime.getTime() + 3600000);
            break;
        case 2:
            var d = new Date(cTime);
            d.setDate(d.getDate() + 1);
            nTime = new Date(d.setHours(8, 0, 0));
            break;
        default:
            nTime = new Date(cTime.getTime() + 10800000);
    }

    var text = '<div class="day_schedule_container">' +
                    '<div class="day_schedule_number_container">' +
                        '<div class="day_schedule_number">' + cnt + '</div>' +
                    '</div>' +
                    '<div class="day_schedule_content_container">' +
                        '<div class="day_schedule_image">' +
                            '<img src="' + imgSrc + '">' +
                        '</div>' +
                        '<div class="day_schedule_content">' +
                            '<div class="day_schedule_title">' +
                                '<a class="day_schedule_title_popup" data-placement="right" data-toggle="popover" data-container="body" data-placement="left" type="button" data-html="true">' + name + '</a>' +
                            '</div>' +
                        '<div class="day_schedule_rating">' +
                                star +
                        '</div>' +
                        '<div class="day_schedule_price">' +
                                price + '원' +
                        '</div>' +
                    '</div>' +
                '</div>' +
                '<div class="day_schedule_time_container">' +
                    '<div class="day_schedule_time">' +
                        '<p>' + cTime.format("HH:mm") + '</p>' +
                        '<p>~' + nTime.format("HH:mm") +'</p>' +
                    '</div>' +
                '</div>' +
            '</div>';

    currentTime = nTime;

    totalMoney += schedule['price'];
    $('.day_schedule').append(text);
}

function createTotalCost() {
    var text = '<div class="total_cost_container">' +
                    '<div class="total_title">' +
                        '<span>총 비용</span>' +
                    '</div>' +
                    '<div class="total_content">' +
                        '<span>' + numberWithCommas(totalMoney) + '₩</span>' +
                    '</div>' +
                '</div>'

    $('.day_schedule').append(text);
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function getStar(rating) {
    var star = '';

    for(var i=0; i<rating; i++)
        star += '★';

    for(; i<5; i++)
        star += '☆'

    return star;
}

function createDay(day) {
    var tourDay = "Day" + day;
    var date = new Date($('#startDate').val());
    date.setHours(date.getHours() - 9);
    date.setDate(date.getDate() + (day-1));

    if(day > 1)
        date.setHours(8);


    var dateString = date.format("yyyy.MM.dd (E)");
    var timeString = addZero(date.getHours()) + ':' + addZero(date.getUTCMinutes());

    var text = '<div class="day_info_container">' +
                    '<div class="day_text">' + tourDay + '</div>' +
                    '<div class="day_info">' +
                        '<span class="day_info_date">' + dateString + " " + timeString + ' 출발' + '</span>' +
                    '</div>' +
                '</div>';

    $('.day_schedule').append(text);
}

function createDistance(cnt) {
    var leg = route[cnt]['routes'][0]['legs'][0];
    var distance = leg['distance']['text'];
    var duration_text =  leg['duration']['text'];
    var duration_value = leg['duration']['value'];

    var text = '<div class="day_distance_container">' +
                    '<div class="day_distance_bar">' +
                        '<ul class="day_bar">' +
                            '<li></li>' +
                            '<li></li>' +
                            '<li></li>' +
                        '</ul>' +
                    '</div>' +
                    '<div class="day_distance_text">' +
                        '-> ' + duration_text + ' 이동 (' + distance + ')' +
                    '</div>' +
                '</div>';

    $('.day_schedule').append(text);

    return duration_value * 1000;
}

function addZero(i) {
    if(i < 10)
        i = "0" + i;

    return i;
}

$(document).on('click', '.day_schedule_title_popup', function() {
    var index = $(this).index('.day_schedule_title_popup');

    $(this).popover({
        html: true,
        content: function() {
            var text = showSchedule(route[index]);
            return text;
        }
    });
});

function showSchedule(leg) {
    var text = '<pre>';
    var origin = leg['request']['origin'];
    var destination = leg['request']['destination'];

    try {
        var step = leg['routes'][0]['legs'][0]['steps'];

        for(i in step) {
            var travelMode = step[i]['travel_mode'];
            var distance = step[i]['distance']['text'];
            var duration = step[i]['duration']['text'];
            var instructions = step[i]['instructions'];

            var cnt = parseInt(i) + 1;


            text += '-----------' + cnt + '-----------\n' +
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
                    '도착시간 : ' + arrivalTime + '\n';
            }

        }
    } catch(e) {
    }

    displayRoute(origin, destination);

    return text + '</pre>';
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";

    var weekName = ["일", "월", "화", "수", "목", "금", "토"];
    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

