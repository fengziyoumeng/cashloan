import React from 'react';
var echarts = require('echarts');
require("echarts/theme/macarons.js");
export default React.createClass({
    getInitialState() {
        return {
            menuData: [],
            assetsData: {},
            investmentData: {},
            loading: false,
            data: [],
            data1: [],
            item: [],
            data2: [],
            max: 1000,
            first: true
        }
    },
    fetch(data) {
        var me = this;
        var data3 = [];
        var data1 = [];
        var data2 = [];
        var item1 = [];
        var item2 = [];
        var max = 10000;
        for (let item in data.countPnameUV) {
            item1.push(item.substring(5));
            item2.push(item);
        }
        for (var i = 0; i < item1.length - 1; i++) {
            for (var j = i + 1; j < item1.length; j++) {
                if (item1[i] > item1[j]) {
                    var z = item1[i];
                    item1[i] = item1[j];
                    item1[j] = z;
                    var q = item2[i];
                    item2[i] = item2[j];
                    item2[j] = q;
                }
            }
            data1.push(data.countPnameUV[item2[i]]);
        }
        me.setState({
            data: data3,
            data1: data1,
            data2: data2,
            item: item2,
            max: max,
            first: false
        },() => {
            me.drawBar();
        });
        
    },
    drawBar() {
        var me = this;
        var bar = echarts.init(document.getElementById('bar2'), 'macarons');
        var option = {
            title: {
                text: '15天产品UV数(每20分钟更新)',
                x: 'center',
                y: 10,
                textStyle: {
                    color: '#666',
                    fontWeight: 'normal'
                }
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                },
                textStyle: {
                    align: 'left'
                }
            },
            barWidth: '10',
            legend: {
                orient: 'horizontal',
                x: 'right',
                y: 50,
                itemGap: 12,
                data: ['产品UV数']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: me.state.item
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitLine: { show: false },
                    scale: true,
                    name: 'UV数',
                    max: me.state.max % 2 == 0 ? me.state.max : me.state.max + 1,
                    min: 0,
                }
            ],
            series: [
                {
                    name: '产品UV数',
                    type: 'bar',
                    data: me.state.data1
                }
            ]
        };
        bar.setOption(option);
    },
    componentWillReceiveProps(nextProps) {
		if(nextProps.data.constructor == Object && this.state.first){
            this.fetch(nextProps.data);
        }
        
    },
    render() {
        return <div id="bar2" style={{ height: '300px', width: '1500px', margin: '0 auto' }}></div>
    }
});