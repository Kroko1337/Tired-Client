#version 120

uniform vec3 u_color;
uniform sampler2D currentTexture;
uniform vec2 texelSize;
uniform vec2 coords;
uniform float blurRadius;
uniform float blursigma;


float gauss(float x) {
    float sq = x / blursigma;
    return 1.0 / (abs(blursigma) * 2.50662827) * exp(-0.5 * sq * sq);
}

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
    vec4 color = vec4(0);
    for (float x = -blurRadius; x <= blurRadius; x++) {
        color += texture2D(currentTexture, gl_TexCoord[0].xy + vec2(texelSize.x * coords.x, texelSize.y * coords.y) * x) * gauss(x);
    }

    gl_FragColor = vec4(pow(u_color.rgb, vec3(1.0/1.0)), pow(color.a, 1.0/1.0));
}