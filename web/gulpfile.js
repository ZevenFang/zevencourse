/**
 * Created by fangxw on 2016/3/26.
 */

var gulp = require("gulp");
var concat = require("gulp-concat");
var uglify = require('gulp-uglify');
var cssmin = require('gulp-minify-css');
var watch = require('gulp-watch');
var del = require('del');
var imagemin = require('gulp-imagemin');
var pngquant = require('imagemin-pngquant');
var ngAnnotate = require('gulp-ng-annotate');
var sourcemaps = require('gulp-sourcemaps');
var livereload = require('gulp-livereload');

var paths = {
    scripts: ['js/app.js', 'js/config.js', 'js/*.js', 'js/services/*.js', 'js/directives/*.js', 'js/controllers/*.js'],
    scriptVendor: ['js/vendor/jquery.min.js', 'js/vendor/angular.js', 'js/vendor/*', 'js/vendor/angular/*', 'js/vendor/jquery/*'],
    styles: ['css/*.css'],
    styleVendor: ['css/vendor/bootstrap.css','css/vendor/*.css'],
    html: ['tpl/**/*.html']
};

gulp.task('clean', function() {
    del(['dist']);
});

gulp.task('css-vendor', function() {
    return gulp.src(paths.styleVendor)
        .pipe(cssmin())
        .pipe(concat('vendor.css'))
        .pipe(gulp.dest('dist/css'))
});

gulp.task('css', function() {
    return gulp.src(paths.styles)
        .pipe(cssmin())
        .pipe(concat('bundle.css'))
        .pipe(gulp.dest('dist/css'))
});

gulp.task('js-vendor', function() {
    return gulp.src(paths.scriptVendor)
        .pipe(concat('vendor.min.js'))
        .pipe(sourcemaps.init())
        .pipe(uglify())
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest('dist/js'))
});

gulp.task('js', function() {
    // bundle.min.js
    gulp.src(paths.scripts)
        .pipe(concat('bundle.min.js'))
        .pipe(ngAnnotate())
        .pipe(sourcemaps.init())
        .pipe(uglify())
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest('dist/js'));
});

gulp.task('js-watch', function() {
    watch(paths.scripts, function() {
        gulp.src(paths.scripts)
            .pipe(concat('bundle.min.js'))
            .pipe(ngAnnotate())
            .pipe(sourcemaps.init())
            .pipe(uglify())
            .pipe(sourcemaps.write('./'))
            .pipe(gulp.dest('dist/js'))
            .pipe(livereload())
    });
    watch(paths.scriptVendor, function() {
        gulp.src(paths.scriptVendor)
            .pipe(concat('vendor.min.js'))
            .pipe(sourcemaps.init())
            .pipe(uglify())
            .pipe(sourcemaps.write('./'))
            .pipe(gulp.dest('dist/js'))
            .pipe(livereload())
    });
});

gulp.task('images', function () {
    return gulp.src('imgs/**/*.*')
        .pipe(imagemin({
            optimizationLevel: 3,
            progressive: true,
            svgoPlugins: [
                {removeViewBox: false}
            ],
            use: [pngquant()]
        }))
        .pipe(gulp.dest('dist/images'));
});

gulp.task('html-watch', function() {
    return gulp.src(paths.html)
        .pipe(watch(paths.html))
        .pipe(livereload())
});

gulp.task('css-watch', function() {
    watch('css/vendor/*.css', function() {
        gulp.src(paths.styleVendor)
            .pipe(cssmin())
            .pipe(concat('vendor.css'))
            .pipe(gulp.dest('dist/css'))
            .pipe(livereload())
    });
    watch('css/*.css', function() {
        gulp.src(paths.styles)
            .pipe(cssmin())
            .pipe(concat('bundle.css'))
            .pipe(gulp.dest('dist/css'))
            .pipe(livereload())
    })
});

gulp.task('livereload', function() {
    livereload.listen();
});

gulp.task('watch', ['default', 'livereload','html-watch', 'css-watch', 'js-watch']);

gulp.task('default', ['css-vendor', 'css', 'js-vendor', 'js']);
