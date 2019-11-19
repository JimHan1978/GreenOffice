function Lottery(id, cover, coverType, width, height, drawPercentCallback) {
    this.conId = id;
    this.conNode = document.getElementById(this.conId);
    this.cover = cover;
    this.coverType = coverType;
    this.background = null;
    this.backCtx = null;
    this.mask = null;
    this.maskCtx = null;
    this.lottery = null;
    this.lotteryType = 'image';
    this.width = width || 300;
    this.height = height || 100;
    this.clientRect = null;
    this.drawPercentCallback = drawPercentCallback;
}

Lottery.prototype = {
    createElement: function (tagName, attributes) {
        var ele = document.createElement(tagName);
        for (var key in attributes) {
            ele.setAttribute(key, attributes[key]);
        }
        return ele;
    },
    getTransparentPercent: function(ctx, width, height) {
        var imgData = ctx.getImageData(0, 0, width, height),
            pixles = imgData.data,
            transPixs = [];
        for (var i = 0, j = pixles.length; i < j; i += 4) {
            var a = pixles[i + 3];
            if (a < 128) {
                transPixs.push(i);
            }
        }
        return (transPixs.length / (pixles.length / 4) * 100).toFixed(2);
    },
    resizeCanvas: function (canvas, width, height) {
        canvas.width = width;
        canvas.height = height;
        canvas.getContext('2d').clearRect(0, 0, width, height);
    },
    drawPoint: function (x, y) {
        this.maskCtx.beginPath();
        var radgrad = this.maskCtx.createRadialGradient(x, y, 0, x, y, 30);
        radgrad.addColorStop(0, 'rgba(0,0,0,0.6)');
        radgrad.addColorStop(1, 'rgba(255, 255, 255, 0)');
        this.maskCtx.fillStyle = radgrad;
        this.maskCtx.arc(x, y, 30, 0, Math.PI * 2, true);
        this.maskCtx.fill();
        if (this.drawPercentCallback) {
            this.drawPercentCallback.call(null, this.getTransparentPercent(this.maskCtx, this.width, this.height));
        }
    },
    bindEvent: function () {
        var _this = this;
        var device = (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
        var clickEvtName = device ? 'touchstart' : 'mousedown';
        var moveEvtName = device? 'touchmove': 'mousemove';
        if (!device) {
            var isMouseDown = false;
            document.addEventListener('mouseup', function(e) {
                isMouseDown = false;
            }, false);
        } else {
            document.addEventListener("touchmove", function(e) {
                if (isMouseDown) {
                	//e.stopPropagation();
                    e.preventDefault();
                }
            }, false);
            document.addEventListener('touchend', function(e) {
                isMouseDown = false;
            }, false);
        }
        
        this.mask.addEventListener(clickEvtName, function (e) {
            if(_this.isMark){
            	isMouseDown = true;
            	this.style.display="none";
            	drawBillLottery(_this.userId,_this.actId,_this.amount);
            }
            /*var docEle = document.documentElement;
            if (!_this.clientRect) {
                _this.clientRect = {
                    left: 0,
                    top:0
                };
            }
            var x = _this.clientRect.left+(device ? e.touches[0].clientX : e.clientX)  + docEle.scrollLeft - docEle.clientLeft;
            var y = (device ? e.touches[0].clientY : e.clientY) - _this.clientRect.top + docEle.scrollTop - docEle.clientTop;
            _this.drawPoint(x, y);*/
        }, false);

        /*this.mask.addEventListener(moveEvtName, function (e) {
            if (!device && !isMouseDown) {
                return false;
            }
            var docEle = document.documentElement;
            if (!_this.clientRect) {
                _this.clientRect = {
                    left: 0,
                    top:0
                };
            }
            var x =  _this.clientRect.left+(device ? e.touches[0].clientX : e.clientX) + docEle.scrollLeft - docEle.clientLeft;
            var y = (device ? e.touches[0].clientY : e.clientY) - _this.clientRect.top + docEle.scrollTop - docEle.clientTop;
            _this.drawPoint(x, y);
            e.stopPropagation();
        }, false);*/
    },
    drawLottery: function () {
        this.background = this.background || this.createElement('canvas', {
            style: 'position:absolute;left:0.5rem;top:0.5rem;'
        });
        this.mask = this.mask || this.createElement('canvas', {
            style: 'position:absolute;left:0.5rem;top:0.5rem;'
        });

        if (!this.conNode.innerHTML.replace(/[\w\W]| /g, '')) {
            this.conNode.appendChild(this.background);
            this.conNode.appendChild(this.mask);
            this.clientRect = this.conNode ? this.conNode.getBoundingClientRect() : null;
            this.bindEvent();
        }

        this.backCtx = this.backCtx || this.background.getContext('2d');
        this.maskCtx = this.maskCtx || this.mask.getContext('2d');

        if (this.lotteryType == 'image') {
            var image = new Image(),
                _this = this;
            image.onload = function () {
                _this.width = this.width;
                _this.height = this.height;
                _this.resizeCanvas(_this.background, this.width, this.height);
                _this.backCtx.drawImage(this, 0, 0);
                _this.drawMask();
            }
            image.src = this.lottery;
        } else if (this.lotteryType == 'text') {
            this.width = this.width;
            this.height = this.height;
            this.resizeCanvas(this.background, this.width, this.height);
            this.backCtx.save();
            this.backCtx.fillStyle = '#FFF';
            this.backCtx.fillRect(0, 0, this.width, this.height);
            this.backCtx.restore();
            this.backCtx.save();
            var fontSize = 21;
            this.backCtx.font = 'Bold ' + fontSize + 'px Arial';
            this.backCtx.textAlign = 'center';
            this.backCtx.fillStyle = '#F60';
            this.backCtx.fillText(this.lottery, this.width / 2, this.height / 2 + fontSize / 2);
            this.backCtx.restore();
            if(this.isMark){
            	this.drawMask();
            }
            
        }
    },
    drawMask: function() {
        this.resizeCanvas(this.mask, this.width, this.height);
        if (this.coverType == 'color') {
            this.maskCtx.fillStyle = this.cover;
            this.maskCtx.fillRect(0, 0, this.width, this.height);
            this.maskCtx.globalCompositeOperation = 'destination-out';
        } else if (this.coverType == 'image'){
            var image = new Image(),
                _this = this;
            image.onload = function () {
                _this.maskCtx.drawImage(this, 0, 0);
                _this.maskCtx.globalCompositeOperation = 'destination-out';
            }
            image.src = this.cover;
        }
    },
    init: function (lottery, lotteryType) {
        this.lottery = lottery;
        this.lotteryType = lotteryType || 'image';
        this.drawLottery();
    }
}

function initLottery(userId,actId,lotteryAmount){
	var lottery = new Lottery('lotteryContainer', '#CCC', 'color', 210, 150);
	lottery.userId = userId;
	lottery.actId=actId;
	
	lottery.isMark = Number(lotteryAmount)==-1;
	if(lottery.isMark){
		lottery.amount = getRandomStr();
	}else{
		lottery.amount = lotteryAmount;
	}
	lottery.init("恭喜中奖"+lottery.amount+"元", 'text');
}


window.onload = function () {
    /*var lottery = new Lottery('lotteryContainer', '#CCC', 'color', 210, 150);
    //drawPercentNode.innerHTML = '0%';
    var lotteryAmount = getRandomStr();
    lottery.amount = lotteryAmount;
    lottery.init("恭喜中奖"+lotteryAmount+"元", 'text');*/
    
    //lottery.init('http://www.baidu.com/img/bdlogo.gif', 'image');
    
    /*document.getElementById('freshBtn').onclick = function() {
        drawPercentNode.innerHTML = '0%';
        lottery.init(getRandomStr(10), 'text');
    }*/
    
    /*var drawPercentNode = document.getElementById('drawPercent');
    
    function drawPercent(percent) {
        drawPercentNode.innerHTML = percent + '%';
    }*/
}

function getRandomStr() {
	var random = parseInt(5 + (10 - 5) * (Math.random()));
	return random;
}

function drawBillLottery(userId,actId,amount){
     //alert("id: \n" + userId+ "\n月份: " + actId+ "\n金钱: " + amount);
	 $.post("http://hyserver.hyetec.com:8180/office/lottery/drawBillLottery.json",{
        actId:actId,
        userId:userId,
        winAmount:amount
    },function(data,status){
        //alert("当前排名: \n" + data.result + "\n状态: " + status);
    });
}
