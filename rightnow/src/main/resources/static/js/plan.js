var totalMoney = 0;

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

    $.ajax({
        url: "/schedule/make",
        type: "GET",
        data: {
            'location': location,
            'money': money,
            'startDate': startDate,
            'endDate': endDate,
            'theme': theme
        },
        success: function (json) {
            var t = JSON.stringify(json, null, "\t");

            $('#plan_result').text("");
            $('#plan_result').text(t);

            makePlan(json);
        }
    });
});

function makePlan(day) {
    $('.day_schedule').html('');

    if (day[0]['accommodation'] == null && day[0]['dinner'] == null) {
        day_schedule_container_null();
        return;
    }

    for (var i in day) {
        var cnt = 1;

        createDay(parseInt(i) + 1);
        createDistance();

        if(day[i]['breakfast'] != null) {
            createSchedule(day[i]['breakfast'], cnt++);
            createDistance();
        }
        if(day[i]['event'] != null) {
            createSchedule(day[i]['event'], cnt++);
            createDistance();
        }
        if(day[i]['lunch'] != null) {
            createSchedule(day[i]['lunch'], cnt++);
            createDistance();
        }
        if(day[i]['tour'] != null) {
            createSchedule(day[i]['tour'], cnt++);
            createDistance();
        }
        if(day[i]['dinner'] != null) {
            createSchedule(day[i]['dinner'], cnt++);
            createDistance();
        }
        if(day[i]['accommodation'] != null) {
            createSchedule(day[i]['accommodation'], cnt++);
        }
    }

    alert(totalMoney);
}

function day_schedule_container_null() {
    var text = '<div class="day_schedule_container">' +
                    '<div class="day_schedule_no">조건에 맞는 일정이 없습니다.</div>' +
                '</div>'

    $('.day_schedule').html(text).hide().fadeIn(1000);
}

function createSchedule(schedule, cnt) {
    var name = schedule['name'];
    var imgSrc = (schedule['image'] == '') ? '/img/default.png' : schedule['image'];
    var rating = schedule['rating'];
    var star = getStar(rating);
    var price = schedule['price'];
    var latitude = schedule['latitude'];
    var longitude = schedule['longitude'];
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
                                name +
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
                        '<p>09:30</p>' +
                        '<p>~ 11:35</p>' +
                    '</div>' +
                '</div>' +
            '</div>';

    totalMoney += schedule['price'];
    $('.day_schedule').append(text);
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
    var dateString = date.format("yyyy.MM.dd (E)");
    var timeString = addZero(date.getUTCHours()) + ':' + addZero(date.getUTCMinutes());

    var text = '<div class="day_info_container">' +
                    '<div class="day_text">' + tourDay + '</div>' +
                    '<div class="day_info">' +
                        '<span class="day_info_date">' + dateString + " " + timeString + ' 출발' + '</span>' +
                    '</div>' +
                '</div>';

    $('.day_schedule').append(text);
}

function createDistance() {
    var text = '<div class="day_distance_container">' +
                    '<div class="day_distance_bar">' +
                        '<ul class="day_bar">' +
                            '<li></li>' +
                            '<li></li>' +
                            '<li></li>' +
                        '</ul>' +
                    '</div>' +
                    '<div class="day_distance_text">' +
                        '-> 10분 이동' +
                    '</div>' +
                '</div>';

    $('.day_schedule').append(text);
}

function addZero(i) {
    if(i < 10)
        i = "0" + i;

    return i;
}

$(document).on('click', '.day_schedule_title', function() {
    var index = $(this).index('.day_schedule_title') + 1;

    console.log(calcTime(position[index-1], position[index]));
});

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

