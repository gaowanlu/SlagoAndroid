import React from 'react'
import styled  from 'styled-components';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Slider from 'react-slick';

function ImgSlider(props) {
    let sliderSettings = {
        dots: true,
        infinite: true,
        speed: 300,
        slidesToShow: 1,
        slidesToScroll: 1,
        autoplay:false
    };
  return (
    <Container {...sliderSettings}>
      {
        props.postData.imgs.map(imgId=>{
          return <Wrap key={imgId}>
              <SliderImg src={`/apis/getPostImg?id=${imgId}`} />
          </Wrap>
        })
      }
    </Container>
  );
}

const Container = styled(Slider)`
    /* width: 100fr; */
    ul li button{
      &:before{
        /* font-size: 10px; */
        color:#352a2a;
      }
    }
    li.slick-active button:before{
      color:#b12121;
    }
    .slick-list{
      overflow: hi;
    }
    button{
      z-index:1;
    }
    .slick-prev,.slick-next{
      &:before{
       color: #2c1515; 
       /* display :none ; */
      }
    }
    /* width: 100fr; */
    /* overflow-x: hidden; */
`;

const Wrap = styled.div`
  /* background: #211f3f; */
  border-radius: 5px;
  /* height: 230px; */
`;

const SliderImg = styled.img`
  box-sizing: border-box;
  width: 100%;
  height:68vh;
  object-fit: cover;
  border-radius: 5px;
  /* box-shadow: rgb(0 0 0 / 69%) 0px 26px 30px -10px,
    rgb(0 0 0 / 73%) 0px 16px 10px -10px; */
  /* border: 5px #ffffff solid; */
  /* &:hover {
    border: 5px #6dee7e42 solid;
  } */
  cursor: pointer;
  transition-duration: 300ms;
`;

export default ImgSlider;