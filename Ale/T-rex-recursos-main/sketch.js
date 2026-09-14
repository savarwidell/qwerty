let trex, ground, groundInvisible;
let trexImg, groundImg, cloudImg;
let cloud, cloudsGroup;

let obstacle, obstaclesGroup;
let obstacle1, obstacle2, obstacle3, obstacle4, obstacle5, obstacle6;

let gravedad = 0.6;
let fuerzaSalto = -12;
let sueloY = 180;

function preload(){
    trexImg = loadAnimation("trex1.png", "trex3.png", "trex4.png");
    groundImg = loadImage("ground2.png");
    cloudImg = loadImage("cloud.png");

    obstacle1 = loadImage("obstacle1.png");
    obstacle2 = loadImage("obstacle2.png");
    obstacle3 = loadImage("obstacle3.png");
    obstacle4 = loadImage("obstacle4.png");
    obstacle5 = loadImage("obstacle5.png");
    obstacle6 = loadImage("obstacle6.png");
}

function setup(){
    createCanvas(600, 400);

    trex = createSprite(60, sueloY, 20, 50);
    trex.addAnimation("tmovimiento", trexImg);
    trex.scale = 0.8;

    ground = createSprite(0, 214, 400, 10);
    ground.addImage(groundImg);

    groundInvisible = createSprite(0, 205, 600, 10);
    groundInvisible.visible = false;

    cloudsGroup = new Group();
    obstaclesGroup = new Group();
}

function draw(){
    background("rgb(250, 251, 251)");

    ground.velocityX = -5;
    if (ground.x < 0){
        ground.x = ground.width / 2;
    }

    // Gravedad
    trex.velocityY = trex.velocityY + gravedad;

    // Salto fluido con 'ESPACIO' o 'FLECHA ARRIBA'
    if ((keyDown('space') || keyDown(UP_ARROW)) && trex.y >= sueloY){
        trex.velocityY = fuerzaSalto;
    }

    // Detener al tocar el suelo
    if (trex.y > sueloY){
        trex.y = sueloY;
        trex.velocityY = 0;
    }

    spawnClouds();
    spawnObstacles();

    drawSprites();
}

function spawnClouds(){
    if (frameCount % 70 === 0){
        cloud = createSprite(600, 50, 40, 15);
        cloud.y = Math.round(random(30, 80));
        cloud.addImage(cloudImg);
        cloud.scale = 0.8;
        cloud.velocityX = -2;

        cloud.depth = trex.depth;
        trex.depth = trex.depth + 1;

        cloudsGroup.add(cloud);
    }
}

function spawnObstacles(){
    if (frameCount % 60 === 0){
        obstacle = createSprite(600, 190, 10, 40);
        obstacle.velocityX = -5;

        let rand = Math.round(random(1, 6));
        switch(rand) {
            case 1: obstacle.addImage(obstacle1); break;
            case 2: obstacle.addImage(obstacle2); break;
            case 3: obstacle.addImage(obstacle3); break;
            case 4: obstacle.addImage(obstacle4); break;
            case 5: obstacle.addImage(obstacle5); break;
            case 6: obstacle.addImage(obstacle6); break;
            default: break;
        }

        obstacle.scale = 0.5;
        obstacle.lifetime = 300;

        obstaclesGroup.add(obstacle);
    }
}
