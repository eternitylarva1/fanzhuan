#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

// 色相转换函数
vec3 hueShift(vec3 color, float hueAdjust) {
    const vec3 k = vec3(0.57735, 0.57735, 0.57735);
    float cosAngle = cos(hueAdjust);
    return color * cosAngle + cross(k, color) * sin(hueAdjust) + k * dot(k, color) * (1.0 - cosAngle);
}

void main() {
    vec4 texColor = v_color * texture2D(u_texture, v_texCoords);
    // 180 度色相转换，即 π 弧度
    texColor.rgb = hueShift(texColor.rgb, 3.1415926);
    gl_FragColor = texColor;
}