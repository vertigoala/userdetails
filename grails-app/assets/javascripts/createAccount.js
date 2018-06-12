//= encoding UTF-8
// Also depends on autocomplete which is provided by the ala-bootstrap3 template
//= require jqueryValidationEngine
//= require webjars/chosen/1.8.3/chosen.jquery.js
//= require_self

var userdetails = userdetails || {};

userdetails.initCountrySelect = function(chosenSelector, countrySelector, statesSelector, statesUrlPrefix) {
    $(function() {

        $(chosenSelector).chosen({
            disable_search_threshold: 10,
            allow_single_deselect: true
        });

        $(countrySelector).on('change', function (evt, params) {
            var $state = $(statesSelector);
            $state.children('option:not(:first)').remove();
            $state.trigger("chosen:updated");
            $.get(statesUrlPrefix + "?country=" + evt.target.value, function (data) {
                if (data.length == 0) {
                    $state.prop('disabled', true);
                } else {
                    $state.prop('disabled', false);
                    $.each(data, function (i, state) {
                        $state.append($("<option></option>").attr("value", state.isoCode).text(state.name));
                    });
                }
                $state.trigger("chosen:updated");
            }, 'json');
        });
    });
};