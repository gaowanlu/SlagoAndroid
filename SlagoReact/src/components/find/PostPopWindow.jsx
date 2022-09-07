import React from 'react';
import CloseIcon from '@material-ui/icons/Cancel';
import  styled  from 'styled-components';
import ImgSlider from './ImgSlider';
import { ScrollStop,ScrollStart } from '../comon/WindowScrollControll';

export default class PostPopWindow extends React.Component{
    componentDidMount(){
        ScrollStop();
    }
    componentWillUnmount(){
        ScrollStart();
    }
    isContainer=false;
    contentClick=(e)=>{
        //console.log("content点击");
        e.nativeEvent.stopImmediatePropagation();
        //在点击container时 不会触发content 
        //但在点击content时 会触发container,content被触发
        this.isContainer=true;
    }
    containerClick=(e)=>{
        //console.log("container点击");
        if(this.isContainer===false)
            this.props.closeCallback();
        else
            this.isContainer=false;
    }
    render(){
        return (
            <Container onClick={this.containerClick}>
                <Content onClick={this.contentClick}>
                    <Header>
                        <CustomClose onClick={(e)=>{this.props.closeCallback()}}/>
                    </Header>
                    <Slider>
                        <ImgSliderContainer>
                            <ImgSlider {...this.props}/>
                        </ImgSliderContainer>
                    </Slider>
                </Content>
            </Container>
        )
    }
}

const Container=styled.div`
    width:100vw;
    height:100vh;
    position:fixed;
    background-color:#1d1d1f77;
    z-index:10000;
    display:flex;
    justify-content:center;
    align-items:center;
    top:0;
    left:0;
    right:0;
`;

const Content=styled.div`
    width:88vw;
    max-width:1100px;
    height:80vh;
    background-color:#fafafa;
    border-radius:5px;
    z-index:10001;
`;

const Header=styled.div`
    width:100%;
    text-align:right;
    padding:5px 5px 0px 0px;
`;

const CustomClose=styled(CloseIcon)`
    cursor:pointer;
    width:100px;
    height:100px;
`;

const Slider=styled.div`
    position: relative;
`

const ImgSliderContainer=styled.div`
    position: absolute;
    width: 80%;
    left:10%;
`;
