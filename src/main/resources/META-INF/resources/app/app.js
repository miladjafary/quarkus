angular
.module('stockComponent',[])
.component('stockComponent',{
    templateUrl: 'app/stock.template.html',
    controller: function StockController($http) {
        var self = this;
        var handleSuccess = function(message) {
            self.alert.reset();
            self.alert.show = true;
            self.alert.type = 'success';
            self.alert.messages.push(message)
            self.refresh();

            self.form.reset();
            self.form.hide();
        };

        var onError = function(response){
            var errors = response.data;

            self.alert.reset();
            self.alert.type = 'danger';
            self.alert.show = true;
            errors.forEach(function(error){
                self.alert.messages.push(error.description);
            })
        }

        self.form= {
            title: '',
            isShow: false,
            stockId : null,
            stockName: {
                value: null,
                readonly: false
            },
            stockPrice: null,
            hide: function() {
                this.isShow = false;
            },
            show: function() {
                this.isShow = true;
            },
            reset: function() {
                this.stockId = null;
                this.title = '';
                this.stockName.value = null;
                this.stockName.readonly= false;
                this.stockPrice = null;

                this.hide();
            }
        };

        self.alert= {
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

        self.closeForm =function () {
            self.alert.reset();
            self.form.reset();
        }
        self.save = function() {
            if (self.form.stockId == null){
                $http.post('/api/stocks/',{
                    name: self.form.stockName.value,
                    price: self.form.stockPrice
                }).then(function onSuccess(response){handleSuccess('Stock has been successfully created');}, onError);

            } else {
                $http.put('/api/stocks/' + self.form.stockId, {price: self.form.stockPrice})
                    .then(function onSuccess(response){handleSuccess('Stock has been successfully updated');}, onError);
            }
        }

        self.createNewStock = function() {
            self.alert.reset();
            self.form.reset();

            self.form.title = 'New Stock';
            self.form.show();
        }

        self.updateStock = function(stock) {
            self.alert.reset();
            self.form.reset();

            self.form.title = 'Update Stock';
            self.form.stockId = stock.id;
            self.form.stockName.value = stock.name;
            self.form.stockName.readonly = true;
            self.form.stockPrice = stock.price;

            self.form.show();
        }

        self.refresh = function() {
            self.stocks=[];
            $http.get('/api/stocks').then(function(response){
                self.stocks = [];
                var stocks = response.data;
                if (stocks != null && stocks.length > 0) {
                    stocks.forEach(function(stock){
                        stock.lastUpdate = Date.parse(stock.lastUpdate);
                        self.stocks.push(stock);
                    })
                }
            })
        };

        self.refresh();
    }
});

angular.module('stockApp', [
    'stockComponent'
]);

