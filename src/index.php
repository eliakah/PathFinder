<?php
// Turn off all error reporting
error_reporting(0);

$latLong = $_GET['latLong'];
$ar = (explode (",",$latLong));
$lat = $ar[0];
$long = $ar[1];
//echo 'result = '.$lat.', '.$long.' ';

$im = imagecreatefrompng('http://maps.googleapis.com/maps/api/staticmap?center='.$lat.','.$long.'&zoom=21&format=png&sensor=false&path=color%3ablack|weight:1|fillcolor%3ablack|-77.05788457660967,38.87253259892824,100path=color%3a0xCC3300|weight:1|fillcolor%3ablack|-77.05465973756702,38.87291016281703,100path=color%3a0xCC3300|weight:1|fillcolor%3a0x000000FF|-77.05315536854791,38.87053267794386,100path=color%3a0x000000FF|weight:1|fillcolor%3ablack|-77.05552622493516,38.868757801256,100path=color%3ablack|weight:1|fillcolor%3ablack|-77.05844056290393,38.86996206506943,100path=color%3ablack|weight:1|fillcolor%3ablack|-77.05788457660967,38.87253259892824,100
&size=1x1&maptype=roadmap&style=feature:administrative|visibility:off&style=feature:landscape|color:0x000000&style=feature:water|color:0xffffff&style=feature:road|visibility:off&style=feature:transit|visibility:off&style=feature:poi|visibility:off&key=AIzaSyDxHFdrjoh7jkYtd8F3YYEY5XcKj_W2Vj8');
//get pixel color, put it in an array
$color_index = imagecolorat($im, 0, 0);//Get the index of the color of a pixel
$color_tran = imagecolorsforindex($im, $color_index);//Get the colors for an index

if($color_tran['red'] == 255 && $color_tran['blue'] == 255 && $color_tran['green'] == 255  ){
    //do your thing
    echo "0";//NOT on land
}else{
    echo "1"; //on land
}

?>