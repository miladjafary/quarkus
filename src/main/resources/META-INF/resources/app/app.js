angular
.module('stockList',[])
.component('stockList',{
    templateUrl: 'app/stock-list/stock-list.template.html',
    controller: function StockListController($http) {
        var self = this;
        self.alert={
            type: 'success',
            show: false,
            messages : [],
            reset: function() {
                this.messages = [];
                this.show = false;
            }
        };

        self.deleteStock = function(stockId) {
            self.alert.reset();
            $http.delete('/api/stocks/'+stockId)
                .then(function onSuccess(response){
                    self.alert.show = true;
                    self.alert.type = 'success';
                    self.alert.messages.push("Stock has been successfully deleted")
                    self.refresh();
                },function onError(response){
                    var errors = response.data;

                    self.alert.type = 'danger';
                    self.alert.show = true;
                    errors.forEach(function(error){
                        self.alert.messages.push(error.description);
                    })
                })
        }

        self.refresh = function() {
            $http.get('/api/stocks').then(function(response){
                console.log (response.data);
                self.stocks = response.data;
            })
        };

        self.refresh();
    }
});

angular.module('stockApp', [
    'stockList'
]);

