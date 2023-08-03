jQuery(window).on('load', function() {
    var panZoom = svgPanZoom('#svgDocument', {
        zoomEnabled: true,
        controlIconsEnabled: true,
        fit: true,
        center: true,
        minZoom: 0.5,
        maxZoom: 15,
        zoomScaleSensitivity: 0.5
    });

    var svgObject = document.getElementById('svgDocument');
    if ('contentDocument' in svgObject) {
        var svgDom = svgObject.contentDocument;
    }

    jQuery(window).resize(function(){
        var width = jQuery('#svgDocument').width() - 40;

        var oldAttr = jQuery('#update-button', svgDom).attr('transform');
        var newAttr = oldAttr.replace(/translate[(]\d+[ ]/, 'translate(' + width + ' ');
        jQuery('#update-button', svgDom).attr('transform', newAttr);

        oldAttr = jQuery('#redirect-button', svgDom).attr('transform');
        newAttr = oldAttr.replace(/translate[(]\d+[ ]/, 'translate(' + (width - 5) + ' ');
        jQuery('#redirect-button', svgDom).attr('transform', newAttr);

        oldAttr = jQuery('#print-button', svgDom).attr('transform');
        newAttr = oldAttr.replace(/translate[(]\d+[ ]/, 'translate(' + (width - 5) + ' ');
        jQuery('#print-button', svgDom).attr('transform', newAttr);

        panZoom.resize();
        panZoom.fit();
        panZoom.center();
    });

    if (svgDom.getElementById('mnemonicSVG') != null) {
        var width = jQuery("#svgDocument").width() - 40;

        var refreshButton = document.createElementNS('http://www.w3.org/2000/svg', 'g');
        refreshButton.setAttribute('id', 'update-button');
        refreshButton.setAttribute('transform', 'translate(' + width + ' ' + 7 + ') scale(0.5)');
        refreshButton.setAttribute('class', 'svg-pan-zoom-control');
        refreshButton.addEventListener('click', function(){
            var timer = setInterval(function() {
                var oldAttr = jQuery('#rotateItem', svgDom).attr('transform');
                var rotateValue = oldAttr.match(/rotate[(]\d+[,]/)[0].match(/\d+/)[0];
                var newRotate = parseInt(rotateValue) + 10;
                if (newRotate === 360) {
                    newRotate = 0;
                }
                var newAttr = oldAttr.replace(/rotate[(]\d+[,]/, 'rotate(' + newRotate + ',');
                jQuery('#rotateItem', svgDom).attr('transform', newAttr);
            }, 30);
            loadData("instLoad", timer);
        }, false);

        var refreshButtonTitle = document.createElementNS('http://www.w3.org/2000/svg', 'title');
        refreshButtonTitle.appendChild(document.createTextNode('Загрузить мгновенные данные'));
        refreshButton.appendChild(refreshButtonTitle);

        var refreshButtonBackground = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
        refreshButtonBackground.setAttribute('width', '63.25');
        refreshButtonBackground.setAttribute('height', '63.16');
        refreshButtonBackground.setAttribute('class', 'svg-pan-zoom-control-background');
        refreshButton.appendChild(refreshButtonBackground);

        var refreshButtonShape = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        refreshButtonShape.setAttribute('d', 'm15.052646,24.168085c3.738297,7.170433 4.468278,9.348475 3.233338,9.642054c-2.416078,0.577682 -1.932068,3.882431 1.448059,9.874184c4.242844,7.516037 12.545685,12.130386 20.344238,11.306778c3.027908,-0.319366 6.449707,-0.981079 7.605286,-1.467072c1.15535,-0.487976 0.446228,0.419708 -1.577087,2.015549c-10.117355,7.983566 -22.613266,7.426559 -32.581238,-1.450043c-3.975221,-3.537842 -9.446106,-13.881218 -9.446106,-17.857368c0,-1.126137 -0.900574,-2.21727 -2.002365,-2.425995c-1.802261,-0.340256 -1.560257,-1.232887 2.407028,-8.890688c2.424011,-4.68338 4.710967,-8.849577 5.074158,-9.260718c0.366791,-0.411087 2.836609,3.421432 5.49469,8.51332l0,0l-0.000001,0zm32.142945,13.823168c-3.738281,-7.170465 -4.468262,-9.348505 -3.233337,-9.642084c2.416077,-0.578285 1.932068,-3.882538 -1.452026,-9.874138c-4.239212,-7.516021 -12.541685,-12.130409 -20.340269,-11.306762c-3.028152,0.319366 -6.449693,0.980393 -7.605289,1.467041c-1.155593,0.487975 -0.446166,-0.419678 1.57286,-2.015397c10.121584,-7.983727 22.617495,-7.426323 32.585466,1.450058c3.976349,3.537689 9.446106,13.880401 9.446106,17.857169c0,1.126297 0.900574,2.217712 2.00238,2.425995c1.802246,0.337784 1.560242,1.232925 -2.407043,8.890688c-2.424011,4.68338 -4.710968,8.849495 -5.074158,9.260567c-0.366791,0.411285 -2.836609,-3.42128 -5.49469,-8.513138l0,0z');
        refreshButtonShape.setAttribute('class', 'svg-pan-zoom-control-element');
        refreshButtonShape.setAttribute('id', 'rotateItem');
        refreshButtonShape.setAttribute('transform', 'rotate(0, 31.125, 31.08)');
        refreshButton.appendChild(refreshButtonShape);

        var svgContent = svgDom.getElementById('mnemonicSVG');
        svgContent.appendChild(refreshButton);

        addRedirectButton('mnemonicSVG', svgDom);

        var print = document.createElementNS('http://www.w3.org/2000/svg', 'g');
        print.setAttribute('id', 'print-button');
        print.setAttribute('transform', 'translate(' + (width - 5) + ' 100) scale(0.08)');
        print.setAttribute('class', 'svg-pan-zoom-control');
        print.addEventListener('click', printSvg);

        var printBackground = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
        printBackground.setAttribute('width', '500');
        printBackground.setAttribute('height', '500');
        printBackground.setAttribute('class', 'svg-pan-zoom-control-background');
        print.appendChild(printBackground);

        var printPath1 = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        printPath1.setAttribute('d', 'M399.25,98.9h-12.4V71.3c0-39.3-32-71.3-71.3-71.3h-149.7c-39.3,0-71.3,32-71.3,71.3v27.6h-11.3    ' +
            'c-39.3,0-71.3,32-71.3,71.3v115c0,39.3,32,71.3,71.3,71.3h11.2v90.4c0,19.6,16,35.6,35.6,35.6h221.1c19.6,0,35.6-16,' +
            '35.6-35.6    v-90.4h12.5c39.3,0,71.3-32,71.3-71.3v-115C470.55,130.9,438.55,98.9,399.25,98.9z M121.45,71.3c0-24.4,' +
            '19.9-44.3,44.3-44.3h149.6    c24.4,0,44.3,19.9,44.3,44.3v27.6h-238.2V71.3z M359.75,447.1c0,4.7-3.9,8.6-8.6,' +
            '8.6h-221.1c-4.7,0-8.6-3.9-8.6-8.6V298h238.3    V447.1z M443.55,285.3c0,24.4-19.9,44.3-44.3,' +
            '44.3h-12.4V298h17.8c7.5,0,13.5-6,13.5-13.5s-6-13.5-13.5-13.5h-330    c-7.5,0-13.5,6-13.5,13.5s6,13.5,13.5,' +
            '13.5h19.9v31.6h-11.3c-24.4,0-44.3-19.9-44.3-44.3v-115c0-24.4,19.9-44.3,44.3-44.3h316    c24.4,0,44.3,19.9,44.3,' +
            '44.3V285.3z');
        print.appendChild(printPath1);

        var printPath2 = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        printPath2.setAttribute('d', 'M154.15,364.4h171.9c7.5,0,13.5-6,13.5-13.5s-6-13.5-13.5-13.5h-171.9c-7.5,0-13.5,6-13.5,' +
            '13.5S146.75,364.4,154.15,364.4    z');
        print.appendChild(printPath2);

        var printPath3 = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        printPath3.setAttribute('d', 'M327.15,392.6h-172c-7.5,0-13.5,6-13.5,13.5s6,13.5,13.5,13.5h171.9c7.5,0,13.5-6,' +
            '13.5-13.5S334.55,392.6,327.15,392.6z');
        print.appendChild(printPath3);

        var printPath4 = document.createElementNS('http://www.w3.org/2000/svg', 'path');
        printPath4.setAttribute('d', 'M398.95,151.9h-27.4c-7.5,0-13.5,6-13.5,13.5s6,13.5,13.5,13.5h27.4c7.5,0,13.5-6,' +
            '13.5-13.5S406.45,151.9,398.95,151.9z');
        print.appendChild(printPath4);

        var printTitle = document.createElementNS('http://www.w3.org/2000/svg', 'title');
        printTitle.appendChild(document.createTextNode('Печать'));
        print.appendChild(printTitle);

        svgContent.appendChild(print);

        loadData("load", null);
    }

    if (svgDom.getElementById('error_svg') != null) {
        addRedirectButton('error_svg', svgDom);
    }
});

function addRedirectButton(elementId, svgDom) {
    var width = jQuery("#svgDocument").width() - 40;
    var svgContent = svgDom.getElementById(elementId);

    var redirect = document.createElementNS('http://www.w3.org/2000/svg', 'g');
    redirect.setAttribute('id', 'redirect-button');
    redirect.setAttribute('class', 'svg-pan-zoom-control');
    redirect.setAttribute('transform', 'translate(' + (width - 5) + ' ' + 50 + ') scale(0.4)');

    var iframeID = document.getElementById('dataForm:iframeID').value;
    if (iframeID === "") {
        redirect.setAttribute('onclick', 'top.redirectJSF()');
    } else {
        redirect.setAttribute('onclick', 'top.document.getElementById(\'test\').contentWindow.redirectJSF()');
    }

    var redirectPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    redirectPath.setAttribute("d", "M69.05,58.1c-4.8,0-9.1,2.3-11.8,5.8l-24.3-14.1c1.5-3.7,1.5-7.8,0-11.5l24.3-14.1c2.7,3.5,7,5.8,11.8,5.8" +
        "c8.3,0,15-6.7,15-15s-6.7-15-15-15s-15,6.7-15,15c0,2,0.4,4,1.1,5.7l-24.3,14.2c-2.8-3.5-7-5.8-11.8-5.8c-8.3,0-15,6.7-15,15" +
        "s6.7,15,15,15c4.8,0,9.1-2.3,11.8-5.8l24.3,14.1c-0.7,1.7-1.1,3.7-1.1,5.7c0,8.3,6.7,15,15,15s15-6.7,15-15S77.35,58.1,69.05,58.1z" +
        " M69.05,4.1c6.1,0,11,4.9,11,11s-4.9,11-11,11c-6.1,0-11-4.9-11-11S62.95,4.1,69.05,4.1z M19.05,55.1c-6.1,0-11-4.9-11-11" +
        "s4.9-11,11-11s11,4.9,11,11S25.15,55.1,19.05,55.1z M69.05,84.1c-6.1,0-11-4.9-11-11s4.9-11,11-11c6.1,0,11,4.9,11,11" +
        "S75.15,84.1,69.05,84.1z");

    var redirectBackground = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
    redirectBackground.setAttribute('width', '85');
    redirectBackground.setAttribute('height', '85');
    redirectBackground.setAttribute('class', 'svg-pan-zoom-control-background');
    redirect.appendChild(redirectBackground);

    redirect.appendChild(redirectPath);

    var redirectTitle = document.createElementNS('http://www.w3.org/2000/svg', 'title');
    redirectTitle.appendChild(document.createTextNode('Мнемосхема сети'));
    redirect.appendChild(redirectTitle);

    svgContent.appendChild(redirect);
}

function loadData(url, timer) {
    var svgObject = document.getElementById('svgDocument');
    if ('contentDocument' in svgObject) {
        var svgDom = svgObject.contentDocument;
    }

    // var search = location.search;
    // var objectId = {
    //     "objId" : search.substring(search.indexOf("objectId") + 9)
    // };

    // Не работает в старых версиях chrome(44)
    var urlParams = new URLSearchParams(location.search);
    var objectAndSessionId = {
        "objId" : urlParams.get("objectId"),
        "sessionID" : urlParams.get("sessionID")
    };

    jQuery.ajax({
        url: url,
        method: "post",
        data: objectAndSessionId,
        error: [
            function(message) {
                console.log(message.responseText);
            }
        ],
        success: [
            function(data) {
                for(var i in data) {
                    if (data.hasOwnProperty(i)) {
                        jQuery("#" + data[i].name, svgDom).changeVisible();
                        jQuery("#" + data[i].name, svgDom).text(data[i].data);

                        jQuery("#" + data[i].name + "_tit", svgDom).text(data[i].title);

                        jQuery("#" + data[i].name + "_col", svgDom).changeColor(data[i].color);

                        jQuery("#" + data[i].name + "_pic", svgDom).changeVisible();
                    }
                }

                if (timer != null) {
                    clearInterval(timer);
                }
            }
        ]
    });
}

jQuery.fn.changeColor = function (color) {
    return this.each(function() {
        jQuery(this).attr("fill", color);
    });
};

jQuery.fn.changeVisible = function () {
    return this.each(function() {
        jQuery(this).attr("opacity", 1);
    });
};

function printSvg() {
    var popUpAndPrint = function() {
        var svgObject = document.getElementById('svgDocument');
        if ('contentDocument' in svgObject) {
            var svgDom = svgObject.contentDocument;
        }

        var width = jQuery('#mnemonicSVG', svgDom)[0].getAttribute('width');
        var height = jQuery('#mnemonicSVG', svgDom)[0].getAttribute('height');

        var printWidth = 1024;
        var printHeight = 700;

        var printWindow = window.open('', 'PrintMap',
            'width=' + (printWidth + 10) + ',height=' + (printHeight + 10));

        var scaleWidth = 1;
        var scaleHeight = 1;
        var scale;

        if (width > printWidth) {
            scaleWidth = printWidth / width;
        }

        if (height > printHeight) {
            scaleHeight = printHeight / height;
        }

        scale = Math.min(scaleWidth, scaleHeight);

        var svg = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
        svg.setAttribute('width', printWidth.toString());
        svg.setAttribute('height', printHeight.toString());

        svg.appendChild(jQuery('.svg-pan-zoom_viewport', svgDom)[0].cloneNode(true));

        printWindow.document.writeln(svg.outerHTML);

        jQuery('.svg-pan-zoom_viewport', printWindow.document).removeAttr('transform');
        jQuery('.svg-pan-zoom_viewport', printWindow.document).attr('style', 'transform: translate(' + (printWidth - (width * scale))/2 + 'px, 0px) scale(' + scale + ');');

        printWindow.document.close();
        printWindow.print();

        setTimeout(function () { printWindow.close(); }, 500);
    };
    setTimeout(popUpAndPrint, 500);
}