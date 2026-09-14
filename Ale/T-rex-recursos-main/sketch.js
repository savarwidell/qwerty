function preload(){
    // carga de imágenes al juego
    trexImg = loadAnimation("trex1.png", "trex3.png", "trex4.png")
    groundImg = loadImage("ground2.png")
    // load obstacles:
    obstacle1 = loadImage("")
}

function setup(){
    // Definición de elementos
    createCanvas(600, 400)
    
    trex = createSprite(60, 154, 20, 50)
    trex.addAnimation("tmovimiento", trexImg)
    trex.scale = 1.8

    ground = createSprite(0, 214, 400, 10)
    ground.addImage(groundImg)

    invisibleGround = createSprite(0, 220, 400, 10)
    invisibleGround.visible = false
}

function draw(){
    background("rgba(186, 70, 199, 0.54)")

    ground.velocityX = -5
    if (ground.x < 0){
        ground.x = ground.width / 2
    }

    // Saltar con la barra espaciadora
    if (keyWentDown("space") && trex.y >= 0){
        trex.velocityY = -12
    }

    // Gravedad
    trex.velocityY = trex.velocityY + 0.8

    // Colisión con el suelo invisible
    trex.collide(invisibleGround)

    drawSprites()
}

function obstacles(){
    if (frameCount % 60 === 0){
        var obstacle = createSprite(600, 190, 10, 40)
        obstacle.velocityX = -6

        ran=Math.round(random(1, 6))
        switch(ran){
            case 1: obstacle.addImage(obstacle1)
                    break; 
            case 2: obstacle.addImage(obstacle2)
                    break;
            case 3: obstacle.addImage(obstacle3)
                    break;
            case 4: obstacle.addImage(obstacle4)
                    break;  
            case 5: obstacle.addImage(obstacle5)
                    break;
            case 6: obstacle.addImage(obstacle6)
                    break;
            default: break

        }
        obstacle.scale=0.5
    }
}