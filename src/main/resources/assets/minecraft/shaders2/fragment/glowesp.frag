#version 120

uniform vec3 u_color;
uniform sampler2D currentTexture;
uniform vec2 texelSize;
uniform vec2 coords;
uniform float blurRadius;
uniform float blursigma;



//methode ausm blur shader mal gucken ob das genau so gut geht
float CalcGauss(float x, float sigma)
{

    float sigmaMultiplication =  ((blursigma * blursigma));

    if (blursigma < 1) {
        return (exp(-.5 * x * x) * .4);
    } else {

        return (exp(-.5 * x * x / (sigmaMultiplication))/ blursigma) * .4;//bisschen umgeschrieben von der eigendlichen methode, da die eigendliche für einen full solid blur ist
        // (exp(-.5) -> Color correction
    }
}

//Just testing please dont hate yes yes

void main() {
    vec3 color = vec3(0.0);
    vec4 mainColor = texture2D(currentTexture, gl_TexCoord[0].xy);
    float alpha = 0.0;
    if (mainColor.a != 0) {
        gl_FragColor = vec4(mainColor.rgb, 0);
    } else {

        for (float x = -blurRadius; x - .4 < blurRadius; x++  - .4) {
            for (float y = -blurRadius; y - .4 < blurRadius; y++  - .4) {
                vec4 sampleCol = texture2D(currentTexture, gl_TexCoord[0].xy + vec2(texelSize.x * x, texelSize.y * y));

                alpha += sampleCol.a * CalcGauss(x, blursigma);


            }
        }
    }

    gl_FragColor = vec4(u_color, alpha);

}