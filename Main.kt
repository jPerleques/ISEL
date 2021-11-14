import pt.isel.canvas.*
import kotlin.math.*

fun main(){
    onStart {
        val arena = Canvas(900,500, WHITE) //will create a panel
        val xCenter = ((arena.width)/2); val yCenter = ((arena.height)/2)
        var head = Head(xCenter, yCenter,60) //will create an initial head in the center of the panel
        arena.Desenhar(xCenter, yCenter,60) //will draw an initial head

        //this event will trigger off when some key from keyboard is clicked
        arena.onKeyPressed {
                //will verify all the hypotheses and run if the hypothesis matches the condition, in this case will verify which key was pressed (left,right,up,down,'+','-')
                key: KeyEvent ->
                when(key.code)
                {
                    LEFT_CODE -> {
                        head = arena.Desenhar(head.xCenter - 4,head.yCenter ,head.raio)
                    }
                    UP_CODE -> {
                        head = arena.Desenhar(head.xCenter,head.yCenter - 4,head.raio)
                    }
                    DOWN_CODE -> {
                        head = arena.Desenhar(head.xCenter,head.yCenter + 4,head.raio)
                    }
                    RIGHT_CODE ->{
                        head = arena.Desenhar(head.xCenter + 4,head.yCenter,head.raio)
                    }

                }

                when(key.char)
                {
                    '+' ->{
                        head = arena.Desenhar(head.xCenter,head.yCenter,head.raio + 1 )
                    }
                    '-' ->{
                        if(head.raio > 0)
                            head = arena.Desenhar(head.xCenter,head.yCenter,head.raio - 1)
                    }
                }
        }

        //this event will trigger off when the mouse button is clicked
        arena.onMouseDown {
            me :MouseEvent ->
                head = arena.Desenhar(me.x,me.y,head.raio)
        }

    }
    onFinish {}
}

//this function will draw the head depending on three values (x coordinate, y coordinate, radius) pre-instanced
//this function will erase all the content from the panel, draw an arc, draw both antennas and both eyes by calling two functions and at the end will return a head with new values
fun Canvas.Desenhar (xCenter:Int,yCenter:Int,radius:Int) : Head{
    this.erase()
    val raio = radius
    this.drawArc(xCenter , yCenter,raio,0,180, GREEN)//Arco da cabeça
    this.DrawAntenna(xCenter,yCenter,raio)
    this.DrawEye(xCenter,yCenter,raio)
    val head = Head(xCenter,yCenter,raio)
    return head
}

//this function will draw both antenna
fun Canvas.DrawAntenna(xCenter:Int,yCenter:Int,raio:Int){
    //constants created to simplify the read of the functions
    val thickness = 0.08*raio //thickness of antenna
    val sen60 = (sqrt(3.0)/2) //sin of 60
    val yfrom =yCenter - (sen60*raio).toInt()
    val yto = (((sqrt(3.0))*(raio+0.3*raio))/2)
    val xto = (((raio+0.3*raio).toInt())/2)
    val ytoArredonda =(yCenter - (raio+0.34*raio)*sen60).toInt()
    val thicknessArredonda = (0.04*raio).toInt()
    val xtoArredonda = (((raio+0.34*raio).toInt())/2)
    //draw the antenna
    this.drawLine( xCenter - (raio/2) ,yfrom,(xCenter - xto) ,(yCenter - yto).toInt(), GREEN,thickness.toInt()) // Right antenna
    this.drawLine(xCenter + (raio/2) ,yfrom,(xCenter + xto) ,(yCenter - yto).toInt(), GREEN,thickness.toInt()) // Left antenna
    //draw an rounded nib
    this.drawCircle(xCenter - xtoArredonda,ytoArredonda,thicknessArredonda, GREEN) // rounded antenna
    this.drawCircle(xCenter + xtoArredonda ,ytoArredonda,thicknessArredonda, GREEN)// rounded antenna

}

//this function will draw both eyes
fun Canvas.DrawEye (xCenter:Int,yCenter:Int,raio:Int){
    //constants created to simplify the read of the functions
    val centerEye = 0.44*raio
    val radiusEye = (0.1*raio).toInt() //radius of the eye
    this.drawCircle((xCenter - centerEye).toInt() , (yCenter - centerEye).toInt() ,radiusEye,WHITE) //Left eye
        this.drawCircle((xCenter + centerEye).toInt() , (yCenter - centerEye).toInt() ,radiusEye,WHITE)//Right eye
}

data class Head (val xCenter: Int, val yCenter: Int, val raio: Int)
