$(document).ready(function(){
    $('form').submit(function(){
        // Show image container
        $("#loader").show();
        $(".container").hide();
    });

    $('.selectRepoBtn').off().on('click', function(){
        $('#selectRepoForm').append('<input type="hidden" name="owner" value="' + $(this).data('owner') + '"/>');
        $('#selectRepoForm').append('<input type="hidden" name="name" value="' + $(this).data('name') + '"/>');
    });

    $('.nav-tabs a').off().on('click', function (e) {
        $('.tab-pane').hide();
        $('#' + $(this).data('tab')).show();
        $('.nav-tabs li').removeClass('active');
        $(this).parent('li').addClass('active');

        if(this.id == 'projectionsLink'){
            var chart = new CanvasJS.Chart("chartContainer", {
                animationEnabled: true,
                theme: "dark1",
                title:{
                    text: 'Last 100 commits'
                },
                data: [{
                    type: "line",
                    indexLabelFontSize: 16,
                    dataPoints: DATAPOINTS
                }]
            });
            chart.render();
        }
    });
});